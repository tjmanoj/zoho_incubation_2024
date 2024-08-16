#include <iostream>
using namespace std;

class stack{
    private:
        int *s;
        int last;
        int size;
    
    public:
        stack(int n): last(0),size(n){          
            s = new int[size];   
        }
        
        void push(int value){
            s[last++] = value;
        }
        
        void pop(){
            if(!empty()){
                last--;
            }
        }
        
        int top(){
            if(!empty()){
                return s[last-1];
            }
            return -1;
        }

        bool empty(){
            return last == 0;
        }
};
bool isValid(string s) {
        stack st(100);
        for(int i=0;i<s.size();i++){
            if(s[i] == '(' || s[i] == '{' || s[i] == '['){
                st.push(s[i]);
            }
            else{
                if(st.empty()) return false;
                else if(s[i] == ')'){
                    if(!st.empty() && st.top() != '(') return false;
                    st.pop();
                }

                else if(s[i] == ']'){
                    if(!st.empty() &&  st.top() != '[') return false;
                    st.pop();
                }
                else if(s[i] == '}'){
                    if(!st.empty() &&  st.top() != '{') return false;
                    st.pop();
                }
            }
        }
            return st.empty();
}
    
int main(){
	string input = "(())";
	cout << isValid(input);
}


