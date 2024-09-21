# -*- coding: utf-8 -*-
"""
Code for CSCU9M6 project

"""

# Import Libraries
import matplotlib.pyplot as plt
import numpy as np
import PIL
import tensorflow as tf

from tensorflow import keras
from tensorflow.keras import layers, regularizers
from tensorflow.keras.layers import Conv2D, Input, MaxPool2D, MaxPooling2D, Dropout, concatenate, UpSampling2D,ZeroPadding2D, Flatten, Dense
from tensorflow.keras.models import Sequential, load_model, Model
from tensorflow.keras.optimizers import Adam, SGD, RMSprop
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint, ReduceLROnPlateau, TensorBoard

from keras.preprocessing.image import ImageDataGenerator

#from google.colab import drive
#drive.mount('/content/drive')

# Parameters
batch_size = 128
img_height, img_width = 200, 200
lr = 0.0001 # 0.01
num_epochs = 15

# Import Data
data_dir = "/content/drive/MyDrive/Colab Notebooks/chest_xray"  # small dataset
#data_dir = "/content/drive/MyDrive/Colab Notebooks/large_chest_xray" # large dataset


data = ImageDataGenerator(rescale = 1./255, # simplifies data by normalizing pixel ranges
                          horizontal_flip=True, # vertical flips make no sense in this context
                          shear_range = 0.2,
                          zoom_range = 0.2, # Zooming in too much might not allow the key area to appear for pneumonia
                          width_shift_range=0.2, # Due to the consistency of the dataset, this should vary little
                          height_shift_range=0.2,
                          brightness_range=(0.8, 1.2), # 20% both ways
                          rotation_range=20, # rotation between -n and n degrees
                          fill_mode='nearest') # any blank space filled by interpolation

test_data = ImageDataGenerator(rescale = 1./255)

# Because we only have two classes, we could use the binary class mode
# class_mode="binary"
# And in our models our last layer would be a single sigmoid, rather than two units of softmax
# and under compile we'd have  loss='binary_crossentropy'
# However, setting this up is tricky because of how the database is configured,
# and we really want a categorical answer, so we use the categorical mode.

training_dataset = data.flow_from_directory(data_dir+"/train", target_size = (img_height, img_width), class_mode="categorical")
validation_dataset = test_data.flow_from_directory(data_dir+"/val", target_size = (img_height, img_width), class_mode="categorical")
testing_dataset = test_data.flow_from_directory(data_dir+"/test", target_size = (img_height, img_width), class_mode="categorical")

# define AlexNet
def AlexNet():
    model=keras.Sequential()
    model.add(Input((img_height,img_width,1)))

    #Here, the number of output channels is much larger than that in LeNet
    model.add(Conv2D(96, (3,3), strides=1, activation='relu'))
    model.add(MaxPool2D((3,3), strides=2))
    # Make the convolution window smaller, set padding to 2 for consistent
    # height and width across the input and output, and increase the
    # number of output channels
    model.add(Conv2D(256, (5,5), padding='same',activation='relu'))
    model.add(MaxPool2D((3,3), strides=2))
    # Use three successive convolutional layers and a smaller convolution
    # window. Except for the final convolutional layer, the number of
    # output channels is further increased. Pooling layers are not used to
    # reduce the height and width of input after the first two  convolutional layers
    model.add(Conv2D(384, (3,3), padding='same',activation='relu'))
    model.add(Conv2D(384, (3,3), padding='same',activation='relu'))
    model.add(Conv2D(256, (3,3), padding='same',activation='relu'))
    model.add(MaxPool2D((3,3), strides=2))
    model.add(Flatten())
    # Here, the number of outputs of the fully-connected layer is several
    #times larger than that in LeNet. Use the dropout layer to mitigate overfitting
    model.add(Dense(4096, activation='relu'))
    model.add(Dropout(0.5))
    model.add(Dense(4096, activation='relu'))
    model.add(Dropout(0.5)),
    model.add(Dense(2, activation="softmax"))
    # compile model
    #opt = SGD(learning_rate=lr, momentum=0.9, weight_decay=0.0005)
    opt=Adam(learning_rate=lr)
    #opt = RMSprop(learning_rate=lr)
    model.compile(optimizer=opt, loss='categorical_crossentropy', metrics=['accuracy'])
    return model

def VGGNet():
    model=keras.Sequential()
    model.add(Input((img_height,img_width,1)))
    model.add(ZeroPadding2D((2,2)))
    model.add(Conv2D(64, (3,3), strides=1, padding='same',activation='relu'))
    model.add(Conv2D(64, (3,3), strides=1, padding='same',activation='relu'))
    model.add(MaxPool2D((2,2), strides=2))
    model.add(Conv2D(128, (3,3), strides=1, padding='same',activation='relu'))
    model.add(Conv2D(128, (3,3), strides=1, padding='same',activation='relu'))
    model.add(MaxPool2D((2,2), strides=2))
    model.add(Conv2D(256, (3,3), strides=1, padding='same',activation='relu'))
    model.add(Conv2D(256, (3,3), strides=1, padding='same',activation='relu'))
    model.add(Conv2D(256, (3,3), strides=1, padding='same',activation='relu'))
    model.add(MaxPool2D((2,2), strides=2))
    model.add(Flatten())
    model.add(Dense(4096, activation="relu"))
    model.add(Dense(128, activation="relu"))
    model.add(Dense(2, activation="softmax"))
    # compile model
    #opt = SGD(learning_rate=lr, momentum=0.9, weight_decay=0.0005) #or
    opt=Adam()
    model.compile(optimizer=opt, loss='categorical_crossentropy', metrics=['accuracy'])
    return model

# EfficientNetV2
# set pretrain to either "imagenet" or None
def EffNet(pretrain):
    from keras.layers import GlobalAveragePooling2D
    base_model = keras.applications.EfficientNetV2B0(
        include_top=False,
        weights=pretrain,
        input_tensor=None,
        input_shape=(img_height, img_width, 3),
        pooling=None,
        classes=1000,
        classifier_activation="softmax",
        include_preprocessing=True,
    )
    opt=Adam(learning_rate=lr, metrics=['accuracy'])
    x = base_model.output
    x = GlobalAveragePooling2D()(x)
    x = Dense(1024, activation='relu')(x)
    predictions = Dense(2, activation='softmax')(x)
    model = Model(inputs=base_model.input, outputs=predictions)

    for layer in base_model.layers:
        layer.trainable = False

    model.compile(optimizer='rmsprop', loss='categorical_crossentropy', metrics=['accuracy'])

    trained_model = model.fit(
        training_dataset,
        epochs=num_epochs,
        validation_data=validation_dataset,
        batch_size=batch_size
    )
    for i, layer in enumerate(base_model.layers):
      print(i, layer.name)

    # freeze the first 249 layers and unfreeze the rest:
    for layer in model.layers[:249]:
      layer.trainable = False
    for layer in model.layers[249:]:
      layer.trainable = True

    # recompile the model for these modifications to take effect
    model.compile(optimizer=Adam(lr=0.0001), loss='categorical_crossentropy', metrics=['accuracy'])

    return model

# VGGNet
def VGGNetPretrained():
    from keras.layers import GlobalAveragePooling2D
    base_model = keras.applications.VGG16(
        include_top=False,
        weights="imagenet",
        input_shape=(img_height, img_width, 3),
        pooling=None,
        classes=1000,
        classifier_activation="softmax",
    )
    opt=Adam(learning_rate=lr)
    x = base_model.output
    x = GlobalAveragePooling2D()(x)
    x = Dense(1024, activation='relu')(x)
    predictions = Dense(2, activation='softmax')(x)
    model = Model(inputs=base_model.input, outputs=predictions)
    
    for layer in base_model.layers:
        layer.trainable = False

    model.compile(optimizer='rmsprop', loss='categorical_crossentropy', metrics=['accuracy'])

    trained_model = model.fit(
        training_dataset,
        epochs=num_epochs,
        validation_data=validation_dataset,
        batch_size=batch_size
    )
    for i, layer in enumerate(base_model.layers):
      print(i, layer.name)

    for layer in model.layers[:249]:
      layer.trainable = False
    for layer in model.layers[249:]:
      layer.trainable = True

    model.compile(optimizer=Adam(lr=0.0001), loss='categorical_crossentropy', metrics=['accuracy'])

    return model

model = None
#CNNmodel='AlexNet'
#CNNmodel="VGGNet"
#CNNmodel = "EffNet"
#CNNmodel = "EffNetTrained"
CNNmodel = "VGGNetPretrained"
if CNNmodel=="AlexNet":
    model = AlexNet()
elif CNNmodel=="VGGNet":
    model = VGGNet()
elif CNNmodel=="EffNetTrained":
    img_height, img_width = 224, 224
    model = EffNet("imagenet")
elif CNNmodel=="EffNet":
    img_height, img_width = 224, 224
    model = EffNet(None)
elif CNNmodel=="VGGNetPretrained":
    img_height, img_width = 224, 224
    model = VGGNetPretrained()
model.summary()

callbacks = None
if CNNmodel=="AlexNet":
    callbacks = [EarlyStopping(patience=10, verbose=1),
      ReduceLROnPlateau(factor=0.1, patience=5, min_lr=0.00001, verbose=1),
      ModelCheckpoint('model-AlexNet.h5', verbose=1, save_best_only=True, save_weights_only=True),
      TensorBoard(log_dir='./logs')]
elif CNNmodel=="VGGNet":
    callbacks = [EarlyStopping(patience=10, verbose=1),
      ReduceLROnPlateau(factor=0.1, patience=5, min_lr=0.00001, verbose=1),
      ModelCheckpoint('model-VGGNet.h5', verbose=1, save_best_only=True, save_weights_only=True),
      TensorBoard(log_dir='./logs')]

# Train Model

trained_model = model.fit(
    training_dataset,
    epochs=num_epochs,
    validation_data=validation_dataset,
    callbacks=callbacks,
    batch_size=batch_size
)

# Evaluation

!pip install visualkeras
import visualkeras
visualkeras.layered_view(model)

plt.figure(figsize = (15,6))
plt.title("Learning curve")
plt.plot(trained_model.history["loss"], label="loss")
plt.plot(trained_model.history["val_loss"], label="val_loss")
plt.plot(np.argmin(trained_model.history["val_loss"]), np.min(trained_model.history["val_loss"]), marker="x", color="r", label="best model")
plt.xlabel("Epochs")
plt.ylabel("log_loss")
plt.legend();

plt.figure(figsize = (15,6))
plt.title("Learning curve")
plt.plot(trained_model.history["accuracy"], label="Accuracy")
plt.plot(trained_model.history["val_accuracy"], label="val_Accuracy")
plt.plot(np.argmax(trained_model.history["val_accuracy"]), np.max(trained_model.history["val_accuracy"]), marker="x", color="r", label="best model")
plt.xlabel("Epochs")
plt.ylabel("Accuracy")
plt.legend();

score=model.evaluate(testing_dataset, verbose=1)
print('Test loss:', score[0])
print('Test accuracy:', score[1])