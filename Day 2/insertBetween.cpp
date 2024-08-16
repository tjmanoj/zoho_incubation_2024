#include<iostream>
using namespace std;

int main(){

    int n = 4,target = 10;
    int arr[n] = {-1,2,4,9};

    int resInd = 0;

    for(int i=0;i<n;i++){
        if(arr[i] == target){
            cout << "Already Exists";
            return 0;
        }
        else if(arr[i] < target){
            resInd = i;
        }
        else{
            break;
        }
    }

    if(resInd == n-1){
        cout << arr[resInd] << " NULL";
    }
    else{
        cout << arr[resInd] << ' ' << arr[resInd + 1];
    }

    return 0;
}