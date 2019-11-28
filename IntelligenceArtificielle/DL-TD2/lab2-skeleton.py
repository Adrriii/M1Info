from __future__ import print_function

import tensorflow as tf
import keras
from keras.datasets import mnist
from keras.models import Sequential
from keras.layers import Dense, Dropout
from keras.optimizers import RMSprop
import numpy as np

#print('tensorflow:', tf.__version__)
#print('keras:', keras.__version__)


#load (first download if necessary) the MNIST dataset
# (the dataset is stored in your home direcoty in ~/.keras/datasets/mnist.npz
#  and will take  ~11MB)
# data is already split in train and test datasets
(x_train, y_train), (x_test, y_test) = mnist.load_data()

# x_train : 60000 images of size 28x28, i.e., x_train.shape = (60000, 28, 28)
# y_train : 60000 labels (from 0 to 9)
# x_test  : 10000 images of size 28x28, i.e., x_test.shape = (10000, 28, 28)
# x_test  : 10000 labels
# all datasets are of type uint8


#To input our values in our network Dense layer, we need to flatten the datasets, i.e.,
# pass from (60000, 28, 28) to (60000, 784)
#flatten images
num_pixels = x_train.shape[1] * x_train.shape[2]
x_train = x_train.reshape(x_train.shape[0], num_pixels)
x_test = x_test.reshape(x_test.shape[0], num_pixels)

#Convert to float
x_train = x_train.astype('float32')
x_test = x_test.astype('float32')

#Normalize inputs from [0; 255] to [0; 1]
x_train = x_train / 255
x_test = x_test / 255

num_classes = 10

model = Sequential()
model.add(Dense(units=784, activation='relu', input_dim=x_train.shape[1]))
model.add(Dense(units=10, activation='softmax', input_dim=y_train.shape[0]))

model.compile(loss='categorical_crossentropy',
            optimizer='sgd',
            metrics=['accuracy'])

y_train_categ = keras.utils.to_categorical(y_train)
y_test_categ = keras.utils.to_categorical(y_test)

history = model.fit(x_train, y_train_categ, validation_split=0.2, epochs=100, batch_size=256)
loss_and_metrics = model.evaluate(x_test, y_test_categ, batch_size=128, verbose=0)

print(loss_and_metrics)

import matplotlib.pyplot as plt

plt.plot(history.history['accuracy'])
plt.plot(history.history['val_accuracy'])

plt.title('AI')
plt.xlabel('epoch')
plt.ylabel('accuracy')
plt.legend(['train','val'], loc='upper left')
plt.show()