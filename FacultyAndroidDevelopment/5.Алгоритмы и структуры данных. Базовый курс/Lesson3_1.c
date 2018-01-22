/* Курс Алгоритмы и структуры данных ДЗ №3 Силантьев Михаил*/

/*
1. Попробовать оптимизировать пузырьковую сортировку. Сравнить количество операций сравнения оптимизированной и не оптимизированной программы. Написать функции сортировки, которые возвращают количество операций.
2. *Реализовать шейкерную сортировку.
3. Реализовать бинарный алгоритм поиска в виде функции, которой передается отсортированный массив. Функция возвращает индекс найденного элемента или -1, если элемент не найден.
4. *Подсчитать количество операций для каждой из сортировок и сравнить его с асимптотической сложностью алгоритма.
Достаточно решить 3 задачи. Записывайте в начало программы условие и свою фамилию. Все решения создавайте в одной программе. Со звёздочками выполняйте в том случае, если вы решили задачи без звёздочек. Снабдите программу меню.
*/
#include <stdio.h>
#include <stdlib.h>

void swap(int *a, int *b) {
    *a^=*b;
	*b^=*a;
	*a^=*b;
}

int bubleSort(int* arr,int len){
	int count=0;
	for(int i=0;i<len;i++){
		for(int j=i+1;j<len;j++){
			count++;
			if(arr[i]>arr[j]){
				swap(&arr[i],&arr[j]);			
			}
		}
	}
	return count;
}

int bubleSortOptimize(int* arr,int len){
	int count=0,flag=0;;
	for(int i=0;i<len;i++){
		flag = 1;
		for(int j = (i % 2) ? 0 : 1; j < len - 1; j += 2){
			count++;
			if(arr[j]>arr[j+1]){
				swap(&arr[j],&arr[j+1]);
				flag=0;
			}
		}
		if(flag) break;
	}
	return count;
}

int snakerSort(int* arr,int len){
	    //2. *Реализовать шейкерную сортировку.
		int count=0;
		int left=0;
		int right=len-1;		
		do{
			//Сдвигаем к концу
            for (int i = left; i < right; i++)
                if(arr[i] > arr[i+1]) swap(&arr[i],&arr[i+1]);
            right--; // уменьшаем правую границу
			count++;
            //Сдвигаем к началу
            for (int i = right; i > left ; i--)
                if(arr[i] < arr[i-1]) swap(&arr[i],&arr[i-1]);
            left++; // увеличиваем левую границу
			count++;
        } while (left <= right);
	return count;	
}

int binareSort(int *arr,int len,int value){
	int count=0;
	int left = 0; // левая граница
	int right = len-1; // правая граница
	int mid; //середина
 
	while (count<len) {
		count++;
		mid = (left + right) / 2;
		if (arr[mid] == value){
			break;
		}else if (arr[mid] > value) {
			right = mid - 1; 
		}else left = mid;
	}
	return (arr[mid]==value) ? mid:-1;
}

void initArr(int* arr,int len){
	int i=0;
	while(i<len){
		arr[i]=rand()%100;
		i++;
	}
}

void printArr(int* arr,int len){
	int i=0;
	while(i<len){
		printf("%d ",arr[i]);
		i++;
	}
	puts("");
}
void copyArr(int* arrFrom, int* arrTo, int len) {
	int i;
	for (i = 0; i < len; i++){
		arrTo[i] = arrFrom[i];
	}
}

#define SIZE 20

int main(){
	int arr0[SIZE],arr1[SIZE],arr2[SIZE];	
	initArr(arr0,SIZE);
	printArr(arr0,SIZE);
	copyArr(arr0,arr1,SIZE);
	copyArr(arr0,arr2,SIZE);
	//1. Попробовать оптимизировать пузырьковую сортировку.
	printf("\nпузырьковая обычная-%d пр.\n",bubleSort(arr0,SIZE));	
	printArr(arr0,SIZE);
	printf("\nпузырьковая оптимизированная-%d пр.\n",bubleSortOptimize(arr1,SIZE));	
	printArr(arr1,SIZE);
	//2. *Реализовать шейкерную сортировку.
	printf("\nшейкерная-%d пр.\n",snakerSort(arr2,SIZE));
	printArr(arr2,SIZE);
	//3. Реализовать бинарный алгоритм поиска в виде функции,
	int value=41;
	printf("\nБинарный поиск , число %d индекс массива %d\n",value,binareSort(arr0,SIZE,value));
	
	return 0;
}
