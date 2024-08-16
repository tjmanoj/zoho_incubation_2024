#include <iostream>
#include <string>

using namespace std;


int findSubstringIndex(const string& mainString, const string& substring) {
    int mainLength = mainString.size();
    int subLength = substring.size();

    if (subLength > mainLength) {
        return -1;
    }

    for (int i = 0; i <= mainLength - subLength; ++i) {
        int j;
        for (j = 0; j < subLength; ++j) {
            if (mainString[i + j] != substring[j]) {
                break;
            }
        }

        if (j == subLength) {
            return i;
        }
    }

    return -1;
}

int main() {
    string mainString, substring;
    
    mainString = "Manoj Tj";
    substring = "Tj";

    int index = findSubstringIndex(mainString, substring);

    cout << index;
    }

    return 0;
}

