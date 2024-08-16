#include <iostream>
using namespace std;

int main() {
    int n = 4;
    int current, step, number = 0, previous = 0;

    for (int row = 1; row <= n; row++) {
        current = previous + (row % 2 == 1 ? 1 : 2 * row - 2);
        previous = current;
        cout << current << '\t';

        for (int col = 2; col <= n; col++) {
            if ((row % 2 == 1 && col % 2 == 1) || (row % 2 == 0 && col % 2 == 0)) {
                current += min(col, n - row + 1);
                current += min(col - 1, n - row + 1);
                --current;
                cout << current << '\t';
            } 
            
            else {
                current += min(row, n - col + 1);
                current += min(row, n - (col - 1) + 1);
                --current;
                cout << current << '\t';
            }
        }

        cout << endl << endl;
    }

    return 0;
}

// 1       2       6       7

// 3       5       8       13

// 4       9       12      14

// 10      11      15      16