#include<iostream>
using namespace std;

int main(){
	int n = 4;
	int startNum = 1;
	
	for(int i=1;i<=n;i++){
		int value = startNum;
		for(int j=1;j<=n;j++){
			if(i+j >= 1+n){
				cout << value << "\t";
				value = value - j;
			}
			else{
				cout << '\t';
			}
		}
		cout << endl;
		startNum = startNum + (n - i) + 1;
	}
	
}


//                         1
//                 5       2
//         8       6       3
// 10      9       7       4