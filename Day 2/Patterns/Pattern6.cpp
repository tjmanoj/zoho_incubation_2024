#include<iostream>
using namespace std;

int main(){
    string str = "ManojTJ";

    int range = str.size() / 3;
    int valueInd = 0;

    for(int i=0;i<=range;i++){
        for(int j=0;j<=range;j++){
            if(i == 0 || i+j == range || i == range){
                cout << str[valueInd++];
            }
            else{
                cout << ' ';
            }
        }
        cout << endl;
    }
    return 0;
}