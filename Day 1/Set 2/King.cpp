#include <iostream>
using namespace std;

int main() {
    int source_x,source_y, dest_x, dest_y;
    cin >> source_x >> source_y >> dest_x >> dest_y;
    
    while(source_x != dest_x || source_y != dest_y){
        source_x = source_x + (source_x <= dest_x) - (source_x >= dest_x);
        source_y = source_y + (source_y <= dest_y) - (source_y >= dest_y);
        
        cout << '[' << source_x << ',' << source_y << ']' << endl;
    } 
}
