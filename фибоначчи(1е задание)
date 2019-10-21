#include <iostream>
using namespace std;

int main()
{
 int x1, x2, n, help;
 while(n!=-1){
 cin >> n;
 if(n==-1) break;

 int *arr = new int[n];
 arr[0]=1;
 arr[1]=1;
 x1 = 1;
 x2 = 1;

 for(int i=0; i<n-2; i++) {
    help=x2;
    x2+=x1;
    x1=help;
    arr[i+2]= x2;
 }

  //cout << x2 << endl;
  for(int i=0; i < n; i++) {
    cout << arr[i] << " ";
  }
  cout << endl;
  delete arr;
 }

  return 0;
}
