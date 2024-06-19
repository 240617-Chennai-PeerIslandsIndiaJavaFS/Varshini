import java.util.Scanner;
public class Mergetwoarrays {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n1 = sc.nextInt();
        int[] arr1 = new int[n1];
        for(int i = 0; i < n1; i++){
            arr1[i] = sc.nextInt();
        }
        int n2 = sc.nextInt();
        int[] arr2 = new int[n2];
        for(int i = 0; i < n2; i++){
            arr2[i] = sc.nextInt();
        }
        int[] mergedArray = new int[n1 + n2];
        int i = 0, j = 0, k = 0;
        while(i < n1 && j < n2){
            if(arr1[i] <= arr2[j]){
                mergedArray[k++] = arr1[i++];
            } else {
                mergedArray[k++] = arr2[j++];
            }
        }
        while(i < n1){
            mergedArray[k++] = arr1[i++];
        }
        while(j < n2){
            mergedArray[k++] = arr2[j++];
        }
        System.out.print("Merged sorted array: ");
        for (int num : mergedArray) {
            System.out.print(num + " ");
        }
    }
}