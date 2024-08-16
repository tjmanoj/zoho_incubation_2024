#include <iostream>
using namespace std;

int search(int nums[],int n, int target) {
        int low = 0;
        int high = n - 1;

        while(low <= high){
            int mid = low + (high - low)/2;

            if(nums[mid] == target) return mid;

            if(nums[low] <= nums[mid]){                             // if left part is sorted
                if(target >= nums[low] && target <= nums[mid]){
                    high = mid - 1;                                // omit right part
                }
                else low = mid+1;
            }

            else{
                if(target >= nums[mid] && target <= nums[high]){
                    low = mid + 1;
                }
                else high = mid - 1;
            }
        }
        return -1;
    }

int main(){
    int n = 7;
    int numbers[7] = {4,5,6,7,0,1,2};

    int target = 3;

    cout << search(numbers,n, target);
    return 0;
}