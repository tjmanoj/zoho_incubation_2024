#include <iostream>
using namespace std;

int main() {
    int currentNumber = 1;
    int decrementStep = 1;

    int numberOfRows = 5;

    cout << 1 << endl;

    for (int row = 1; row < numberOfRows; row++) {
        currentNumber += decrementStep;

        for (int column = 0; column <= row; column++) {
            cout << currentNumber << " ";
            currentNumber += 2; 
        }

        currentNumber -= 2;

        if (row % 2 == 1) {
            decrementStep -= 2;
        }

        cout << endl;
    }

    return 0;
}


// 1
// 2 4
// 3 5 7
// 6 8 10 12
// 9 11 13 15 17