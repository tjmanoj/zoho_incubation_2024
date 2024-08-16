#include <iostream>
#include <string>
#include <cmath>
using namespace std;


string intToString(int num) {
    if (num == 0) return "0";
    
    string str = "";
    while (num > 0) {
        str = char(num % 10 + '0') + str;
        num /= 10;
    }
    
    return str;
}
bool compareStrings(const string& a, const string& b) {

    string order1 = a + b;
    string order2 = b + a;


    int len1 = order1.length();
    int len2 = order2.length();
    
    for (int i = 0; i < len1 && i < len2; ++i) {
        if (order1[i] > order2[i]) return true;
        if (order1[i] < order2[i]) return false;
    }
    return len1 > len2;
}


void bubbleSort(string arr[], int size) {
    for (int i = 0; i < size - 1; ++i) {
        for (int j = 0; j < size - i - 1; ++j) {
            if (compareStrings(arr[j], arr[j + 1]) == 0) {
                string temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

string formMaxNumber(int arr[], int size) {
    string strArr[size];
    for (int i = 0; i < size; ++i) {
        strArr[i] = intToString(arr[i]);
    }
    

    bubbleSort(strArr, size);
    

    string result = "";
    for (int i = 0; i < size; ++i) {
        result += strArr[i];
    }
    
    return result;
}

int main() {
	int n = 6;
    int arr[n] = {1,10,19,3,7,30};

    string maxNumber = formMaxNumber(arr, n);
    cout << maxNumber << endl;

    return 0;
}

