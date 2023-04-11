# neural-network
Neural-network language classifier written in Java.

PL
----
Projekt stworzony bez implementacji zewnętrznych bibliotek. Program korzysta z perceptronów (neuronów) z dyskretną funkcją aktywacji, tworząc jednowarstwową sieć neuronową. Sieć służy do wykrywania języka dzięki uprzedniemu wstrzyknięciu do programu danych i nauczeniu każdego perceptrona na zasadzie "polski:inny".

Aby skorzystać z programu, należy:

1. Stworzyć dwa foldery przechowywujący dane treningowe i testowe.
2. W nich odpowiednio stworzyć foldery odpowiadające danemu językowi i nazwać go zgodnie z etykietą.
3. Wypełnić foldery z językami dowolną ilością plików tekstowych zawierających tekst w danym języku.
4. Podać jako argumenty: scieżkę folderu treningowego, scieżkę folderu testowego.

ENG
----
Project created without external library implementation. This program uses perceptrons with a discrete activation function (neurons) to create a single-layer neural network. It is then used to detect language by injecting data into the program beforehand and training each perceptron on the principle of "english:other".

To use the program, follow these steps:

1. Create two folders to store training and testing data.
2. Create subfolders within them for each language and name them accordingly.
3. Fill the language subfolders with any number of text files containing text in that language.
4. Provide the training folder path and testing folder path as arguments.
