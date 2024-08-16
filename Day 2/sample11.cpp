#include <iostream>
#include <string>

using namespace std;

// Function to find the index of the first occurrence of a substring in a string
int findSubstringIndex(const string& mainString, const string& substring) {
    int mainLength = mainString.size();
    int subLength = substring.size();

    // Edge case: If the substring is longer than the main string, return -1
    if (subLength > mainLength) {
        return -1;
    }

    // Iterate through the main string
    for (int i = 0; i <= mainLength - subLength; ++i) {
        int j;
        // Check if the substring matches the part of the main string
        for (j = 0; j < subLength; ++j) {
            if (mainString[i + j] != substring[j]) {
                break;
            }
        }

        // If all characters match, return the starting index
        if (j == subLength) {
            return i;
        }
    }

    // If no match is found, return -1
    return -1;
}

int main() {
    string mainString = "Manoj Tj", substring = "Tj";
    getline(cin, substring);

    int index = findSubstringIndex(mainString, substring);

    if (index != -1) {
        cout << "The substring is found at index: " << index << endl;
    } else {
        cout << "The substring is not found." << endl;
    }

    return 0;
}

