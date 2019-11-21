import numpy as np
import math

#In this first part, we just prepare our data (mnist) 
#for training and testing

import keras
from keras.datasets import mnist
(X_train, y_train), (X_test, y_test) = mnist.load_data()
num_pixels = X_train.shape[1] * X_train.shape[2]
X_train = X_train.reshape(X_train.shape[0], num_pixels).T
X_test = X_test.reshape(X_test.shape[0], num_pixels).T
y_train = y_train.reshape(y_train.shape[0], 1)
y_test = y_test.reshape(y_test.shape[0], 1)
X_train = X_train.astype('float32')
X_test = X_test.astype('float32')
y_train = y_train.astype('float32')
y_test = y_test.astype('float32')
X_train  = X_train / 255
X_test  = X_test / 255


#We want to have a binary classification: digit 5 is classified 1 and 
#all the other digits are classified 0

y_new = np.zeros(y_train.shape)
y_new[np.where(y_train==5.0)[0]] = 1
y_train = y_new

y_new = np.zeros(y_test.shape)
y_new[np.where(y_test==5.0)[0]] = 1
y_test = y_new


y_train = y_train.T
y_test = y_test.T


m = X_train.shape[1] #number of examples

#Now, we shuffle the training set
np.random.seed(138)
shuffle_index = np.random.permutation(m)
X_train, y_train = X_train[:,shuffle_index], y_train[:,shuffle_index]


#Display one image and corresponding label 
import matplotlib
import matplotlib.pyplot as plt
i = 3
print('y[{}]={}'.format(i, y_train[:,i]))
plt.imshow(X_train[:,i].reshape(28,28), cmap = matplotlib.cm.binary)
plt.axis("off")
plt.show()


#Let start our work: creating a neural network
#First, we just use a single neuron. 

def sigmoid(z):
    s = 1. / (1. + np.exp(-z))
    return s

def cross_entropy(Y, Y_hat):
    m = Y.shape[1]
    L = -(1./m) * (np.sum(np.multiply(np.log(Y_hat),Y)) + np.sum( np.multiply(np.log(1-Y_hat),(1-Y))))
    return L

def neuron(x, W, b, eval = sigmoid):
    return eval(x * W + b)

#We define the backward propagation, build and train our network:
learning_rate = 1.
X = X_train
Y = y_train
print('Y.shape={}'.format(Y.shape))
n_x = X.shape[0]
m = X.shape[1]
W = np.random.randn(1, n_x) * 0.01
b = np.zeros((1, 1))

for i in range(100):
    Z = np.matmul(W, X) + b
    Y_hat = sigmoid(Z)
    cost = cross_entropy(Y, Y_hat)

    dW = (1./m) * np.matmul((Y_hat-Y), X.T)
    db = (1./m) * np.sum(Y_hat-Y, axis=1, keepdims=True)

    W = W - learning_rate * dW
    b = b - learning_rate * db

    if (i % 20 == 0):
        print("Epoch", i, "cost: ", cost)

print("Final cost:", cost)