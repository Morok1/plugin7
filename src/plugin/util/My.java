package plugin.util;

import plugin.NameCursor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class My extends NameCursor {

    public static void main(String[] args) {
        System.out.println(0^0);
    }

    static  public int majorityElement(int[] nums) {
        Map<Integer, Integer> map  = new HashMap<>();
        for (int i = 0; i <nums.length ; i++) {
            Integer orDefault = map.getOrDefault(nums[i], 0);
            orDefault+=1;
            map.put(nums[i], orDefault);
        }

        for (Integer integer: map.keySet()) {
            if(map.get(integer)>nums.length/2){
                return integer.intValue();
            }
        }
        return -1;


    }

   static String[] domainType(String[] domains) {
        Map<String, String> domainsMap = new HashMap<>();
        domainsMap.put("com", "commercial");
        domainsMap.put("org", "organization");
        domainsMap.put("net", "network");
        domainsMap.put("info", "information");
        String[] result = new String[domains.length];
        for (int i = 0; i <domains.length ; i++) {
            String[] split = domains[i].split("\\.");
            String s = domainsMap.get(split[split.length -1]);
            result[i] = s;
        }
        return result;

    }

   static public String addStrings(String num1, String num2) {
        long l = Long.parseLong(num1) + Long.parseLong(num2);
        return String.valueOf(l);

    }
    static int[] ratingThreshold(double threshold, int[][] ratings) {
        int k =0;
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < ratings.length; i++) {
            if(computeAverage(ratings[i]) < threshold){
                result.add(i);

            }
        }
        int[] result1 = new int[result.size()];
        for (int i = 0; i <result.size() ; i++) {
            result1[i] = result.get(i);
        }
        return result1;
    }
    static double computeAverage(int[] arr){
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum+=arr[i];
        }
        return sum/Double.valueOf(arr.length);
    }
    int reorderingOfProducts(ArrayList<Integer> boundaries, int y) {
        final int INF = Integer.MAX_VALUE;
        boundaries.add(0, -INF);
        int l = 0;
        int r = boundaries.size() - 1;
        while (l + 1 < r) {
            int mid = (l + r) / 2;
            if (y < boundaries.indexOf(mid)) {
                l = mid;
            }
            else {
                r = mid;
            }
        }
        return l;
    }
    String swapCase(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i <chars.length ; i++) {
            chars[i] = Character.isUpperCase(chars[i]) ? Character.toLowerCase(chars[i]): Character.toUpperCase(chars[i]);
        }
       return String.valueOf(chars);
    }

    boolean whitespaceSearchRegExp(String input) {

            return input.contains(" ");
    }
    int[] traverseTree(Tree<Integer> t) {
        List<Integer> list = new ArrayList<>();
        traverseTreeHelper(t, list);
        int[] ints = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ints[i] = list.get(i);
        }
        return ints;
    }

    void traverseTreeHelper(Tree<Integer> t, List<Integer> list){
        if(t!= null){
            if(t.left!= null) traverseTreeHelper(t.left, list);
            list.add(t.value);
            if(t.right!= null) traverseTreeHelper(t.right, list);
        }
    }
    int[] largestValuesInTreeRows(Tree<Integer> t) {
        if(t== null) return null;
        Map<Integer, Integer> map = new ConcurrentHashMap<>();

        traverseTreeHelperPlus(t, map, 0);
        List<Integer> list = map.entrySet().stream().map(s -> s.getValue()).collect(Collectors.toList());
        int[] ints = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ints[i] = list.get(i);
        }
        return ints;
    }
    void traverseTreeHelperPlus(Tree<Integer> t, Map<Integer, Integer> map, int level){
        if(t!= null){
            if(t.left!= null) traverseTreeHelperPlus(t.left, map, level+1);
            boolean b = t.value > map.getOrDefault(level, Integer.MIN_VALUE);
            if(b){map.put(level,t.value);}

            if(t.right!= null) traverseTreeHelperPlus(t.right, map, level+1);
        }
    }
    boolean sumOfTwo(int[] a, int[] b, int v) {
        Map<Integer, Boolean> mapA = initMapFromArray(a);
        Map<Integer, Boolean> mapB = initMapFromArray(b);
        for (int i = 0; i < b.length; i++) {
            if(mapB.getOrDefault(v - b[i], false)){
                return true;
            }
        }
        return false;
    }
    public int[] decompressRLElist(int[] nums) {
        int length = nums.length;
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < length/2; i++) {
               res.addAll(createDuplicateList(2*i+1, 2*i));
        }
        int[] ints = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ints[i] = res.get(i);
        }
        return ints;
    }
    List<Integer> createDuplicateList(int val, int len){
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i <len ; i++) {
            res.add(val);
        }
        return res;
    }
    public int minTimeToVisitAllPoints(int[][] points) {
        int[][] p = points;
        int res = 0;
        for (int i = 0; i <points.length-1 ; i++) {
            res += max(abs(p[i][0]-p[i+1][0]), abs(p[i][1]-p[i+1][1]));
        }
        return res;
    }
    public int oddCells(int n, int m, int[][] indices) {
        int[][] mat = new int[n][m];
        for (int i = 0; i < indices.length; i++) {
            incr(mat, indices[i][0], indices[i][1]);
        }
        int count = 0;
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j < m; j++) {
                count+=(mat[i][j] %2 ==1 ? 1: 0);
            }
        }
        return count;
    }
    public void  incr(int[][] m, int i, int j){
        for (int k = 0; k <m[i].length ; k++) {
            m[i][k]+=1;
        }
        //row
        for (int k = 0; k <m.length ; k++) {
            m[k][j]+=1;
        }
    }

    public int[] sumZero(int n) {
        boolean isOdd =  n%2 ==1;
        int[] res = new int[n];
        if(isOdd){
            for (int i = 0; i <n/2 ; i++) {
                res[i] = i;
            }
            for (int i = 0; i <n/2 ; i++) {
                res[i+n/2] = i*(-1);
            }
            res[n-1] = 0;
        }
        else{
            for (int i = 0; i <n/2 ; i++) {
                res[i] = i;
            }
            for (int i = 0; i <n/2 ; i++) {
                res[i+n/2] = i*(-1);
            }
        }
        return res;
    }

    public int[][] flipAndInvertImage(int[][] A) {
        //reverse
        int C = A[0].length;
        for (int[] row: A) {
            for (int i = 0; i < (C+1)/2 ; i++) {
                int tmp = row[i]^1;
                row[i] = row[C-1-i]^1;
                row[C-1-i] = tmp;
            }
        }
        return A;


    }
//    public int numJewelsInStones(String J, String S) {
//        int length = J.length();
//        Map<Character, String> jew = new HashMap<>();
//        return 0;
//    }
    public int[][] reverseM(int[][] src){
        for (int i = 0; i <src.length ; i++) {
            src[i] = reverse(src[i]);
        }
        return src;
    }

    public int[] reverse(int[] src){
        int[] dest = new int[src.length];
        for (int i = 0; i < dest.length ; i++) {
            dest[i]= src[dest.length -1 -i];
        }
        return dest;
    }

    public int[] replaceElements(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i <res.length-1 ; i++) {
            int[] max = findMax(arr, i);
            for (int j = i; j < max[0] ; j++) {
                res[j] = max[1];
            }
        }
        res[res.length-1] = -1;
        return res;
    }
    public int[] findMax(int[] arr, int i){
        int length = arr.length;
        int[] max = new int[2];
        max[0]=0;
        max[1]=-10000;
        for (int j = i; j <length ; j++) {
            if(arr[i] > max[1]){
                max[0] = j;
                max[1] = arr[j];
            }
        }
        return max;
    }
    public int findNumbers1(int[] nums) {
        int sum = 0;
        for (int i = 0; i <nums.length ; i++) {
            sum+=evenDigit(nums[i]);
        }
        return sum;
    }
    public int evenDigit(int num){
        char[] chars = String.valueOf(num).toCharArray();
        return chars.length % 2 ==0 ? 1 : 0;
    }


    String[] composeRanges(int[] nums) {
        StringBuilder builder = new StringBuilder();
        if(nums.length ==1) return new String[]{String.valueOf(nums[0])};
        List<String> res = new ArrayList<>();
        int max = 0;
        int min = 0;
        for (int i = 0; i <nums.length-1 ; i++) {
            if(nums[i]<nums[i+1]){
                max++;
            }else{
                res.add(String.valueOf(nums[min]) + "->" + String.valueOf(nums[max]));
                min = i+1;
                max = i+1;
            }
        }

        return fromListToArray(res);
    }
    String[] fromListToArray(List<String> list){
        String[] strings = new String[list.size()];
        for (int i = 0; i <list.size() ; i++) {
            strings[i] = list.get(0);
        }
        return strings;
    }

    Integer[] fromListToIntegerArray(List<Integer> list){
        Integer[] Integers = new Integer[list.size()];
        for (int i = 0; i <list.size() ; i++) {
            Integers[i] = list.get(0);
        }
        return Integers;
    }

    int houseRobber(int[] nums) {
        int len = nums.length;
        if(nums.length == 0) return 0;
        int prev1 = 0;
        int prev2 = 0;
        for (int res:nums) {
            int temp = prev1;
            prev1 = max(prev1, prev2 +res);
            prev2 = temp;
        }
        return prev1;
    }

    int[] returnArrBySize(int[] nums, int max){
        int[] ints = new int[max+1];
        for (int i = 0; i <=max ; i++) {
            ints[i] = nums[i];
        }
        return ints;
    }



    private Map<Integer, Boolean> initMapFromArray(int[] a) {
        Map<Integer, Boolean> mapA = new ConcurrentHashMap<>();
        for (int i = 0; i < a.length ; i++) {
            mapA.put(a[i], true);
        }
        return mapA;
    }


    boolean containsDuplicates(int[] a) {
        Arrays.sort(a);
        for (int i = 0; i <a.length-1; i++) {
            if(a[i]==a[i+1])return false;
        }
        return true;
    }

    // Complete the maxSubsetSum function below.
    static int maxSubsetSum(int[] arr) {
        if(arr.length == 0) return 0;
        if(arr.length ==1) return arr[0];

        for (int i = 2; i <arr.length ; i++) {
            arr[i] = max(arr[i-1], arr[i-2] +arr[i]);
        }
        int max =  Integer.MIN_VALUE;
        for (int i = 0; i <arr.length ; i++) {
            max = (arr[i]> max) ? arr[i]:0 ;
        }
return max;
    }

    void inOrder(Tree<Integer> t, int[] ints, int next ){
        if(t!=null){
            inOrder(t.left, ints, next);
            ints[next++] = t.value;
            inOrder(t.left, ints, next);
        }
    }

    int kthSmallestInBST(Tree<Integer> t, int k) {
        int[] ints = new int[2560];
        if(t == null){return 0;}
        inOrder(t, ints, 1);
        return ints[k-1];
    }
    int equationSolutions(int l, int r) {
            int sum = 0;
            for (int i = l; i <r ; i++) {
                for (int j = l; j <r ; j++) {
                    if(i != j && i*i*i == j*j){
                        sum++;
                    }
                }
            }
            return sum/2;
    }

    String properNounCorrection(String noun) {
        char[] chars = noun.toCharArray();
        for (int i = 0; i <chars.length ; i++) {
            chars[i] =(i ==0 ? Character.toUpperCase(chars[i]): Character.toLowerCase(chars[i]));
        }
        return new String(chars);
    }

    boolean isLowerTriangularMatrix(int[][] matrix) {
        int length = matrix.length;
        for (int i = 0; i <length ; i++) {
            for (int j = i+1; j < length; j++) {
                if(matrix[i][j]!=matrix[i][j]) return false;
            }
        }
        return true;
    }

        class Node {
            int data;
            Node left;
            Node right;}

    //
// Binary trees are already defined with this interface:
 class Tree<T> {
   Tree(T x) {
     value = x;
   }
   T value;
   Tree<T> left;
   Tree<T> right;
 }
    boolean isTreeSymmetric(Tree<Integer> t) {
        if(t == null){return true;}
        return treeHelper(t,t);
    }
    public static int height(Node root) {
        if(root==null){
            return 0;
        }
        return heightHelper(root, 0);
    }
    static int heightHelper(Node root, int max){
        if(root!= null)return max-1;
        if(root.left == null && root.right ==null) return max;
        return max(heightHelper(root.left, max+1), heightHelper(root.right, max+1));
    }

    private boolean treeHelper(Tree<Integer> t, Tree<Integer> t1) {
        if(t== null && t1 ==null){
            return true;
        }
        if(t==null || t1==null) return false;
        if(t!= null && t1 !=null){
            return t.value == t1.value;
        }
        if(!(t.value.equals(t1.value))) return false;
        return treeHelper(t.left, t1.right) && treeHelper(t1.left,  t.right);
    }

    // Singly-linked lists are already defined with this interface:
 class ListNode<T> {
   ListNode(T x) {
     value = x;
   }
   T value;
   ListNode<T> next;
 }
//
    boolean isListPalindrome(ListNode<Integer> l) {
        List<Integer> list = new ArrayList<>();
        while(l.next!=null){
            list.add(l.value);
            l = l.next;
        }

        int size = list.size();
        int i = 0;
        int j = size - 1;
        while(i <j){
            if(list.get(i) != list.get(j))return false;
        }
        return true;
    }


    void knapsackLight(int value1, int weight1, int value2, int weight2, int maxW) {
        int sum = 0;
        int s= maxW;
        max(weight1, weight2);
        while(sum< maxW){

        }
    }

    String longestString(String[] inputArray) {
        int max = 0;
        int j = 0;

        for (int i = 0; i < inputArray.length ; i++) {
            if(inputArray[i].length()> max){
                max = inputArray[i].length();
                j = i;
            }
        }
        return inputArray[j];
    }
    public void test(){
        System.out.println("simple");
    }

//    static class ListNode<T> {
//        ListNode(T x) {
//            value = x;
//        }
//
//        T value;
//        ListNode<T> next;
//    }
//
//    static class LinkedListNode<T> {
//        LinkedListNode(T x) {
//            value = x;
//        }
//
//        T value;
//        ListNode<T> next;
//        ListNode<T> prev;
//    }
//
//    static ListNode<Integer> removeKFromList(ListNode<Integer> l, int k) {
//        if(l == null){
//            return null;
//        }
//        ListNode<Integer> first =l;
//        while(first.value == k && first.next!= null){
//            first = first.next;
//        }
//
//        ListNode<Integer> curr =first;
//        ListNode<Integer> prev = null;
//        while(curr.next!=null) {
//            if(curr.value ==k){
//                ListNode<Integer> temp = curr;
//                curr = curr.next;
//                temp.next = null;
//                if (prev!= null) {
//                    prev.next = curr;
//                }
//            } else {
//                prev = curr;
//                curr = curr.next;
//            }
//        }
//        return first;
//    }
//
//    boolean arePrizesOK(int first, int second, int third) {
//        boolean firstSec = first >= second;
//        boolean secThird = second >= third;
//        if (firstSec&& secThird ){
//            return true;
//        }
//    }
//    public int removeElement(int[] nums, int val) {
//        int[] arr = new int[]{};
//        Arrays.sort(nums);
//        for (int i = 0; i <nums.length -1; i++) {
//            if(nums[i] != val){
//                sum++;
//            }
//        }
//        return sum;
//
//    }
//
//
//
//
//    int largestNumber(int n) {
//        int k =9;
//        for (int i = 1; i <n ; i++) {
//            k = k*10+9;
//        }
//        return k;
//    }
//    int divisorsSubset(int[] subset, int n) {
//        int mul=1;
//        for (int i = 0; i <subset.length ; i++) {
//            mul*=subset[i];
//        }
//        int j = 1;
//
//        while(mul*j< n){
//        j++;
//        }
//        return j;
//    }
//
//
//    int arrayMaxConsecutiveSum(int[] inputArray, int k) {
//        Arrays.sort(inputArray);
//        int sum = 0;
//        for (int i = inputArray.length -k; i <inputArray.length ; i++) {
//            sum+=inputArray[i];
//        }return sum;
//    }
//    boolean evenDigitsOnly(int n) {
//        String nth = String.valueOf(n);
//        char[] chars = nth.toCharArray();
//        for (int i = 0; i <chars.length ; i++) {
//            if(chars[i] - '0' %2 !=0){
//                return false;
//            }
//        }
//        return true;
//    }
//    boolean isListPalindrome(ListNode<Integer> l) {
////        int[] inputArray;
////        for (int i = 0; i < inputArray.length; i++) {
////            sum += inputArray[i]
////        }
////        ListNode<Integer> first = l;
////        ListNode<Integer> prev = null;
////        if(first ==null){
////            return true;
////        }
////
////        ListNode<Integer> curr = first.next;
////
////        LinkedListNode<Integer> currLinked = new LinkedListNode<>(first.value);
////        while(curr.next!=null){
////            currLinked.next = curr.next;
////            currLinked.prev = prev;
////            prev = curr;
////        }
////
////
//
//
//
//
//
//        while(curr.next!=null){
//            currLinked.next = curr.next;
//            currLinked.prev = prev;
//            prev = curr;
//        }
//
//        return false;
//    }
//    static ListNode<Integer> removeRecursveKFromList(ListNode<Integer> l, int k) {
//        ListNode<Integer> curr = l;
//        while(curr.next!=null) {
//            if(l.value ==k){
//                ListNode<Integer> temp = curr;
//                curr = curr.next;
//                temp.next = null;
//            } else {
//                curr = curr.next;
//            }
//        }
//            return null;
//    }
//
//    private static int toChar(int[] a) {
//        Map<Integer, List<Integer>> map = new HashMap<>();
//        for (int i = 0; i < a.length; i++) {
//            List<Integer> orDefault = map.getOrDefault(a[i], new ArrayList<>());
//            orDefault.add(i);
//            map.put(a[i], orDefault);
//        }
//        List<Integer> duplicaties = new ArrayList<>();
//        for (int i = 0; i < a.length; i++) {
//            if (map.get(a[i]).size() != 1) {
//                duplicaties.add(a[i]);
//            }
//        }
//        if (!duplicaties.isEmpty()) {
//            Collections.sort(duplicaties, Comparator.comparing(k -> map.get(k).get(1)));
//            return duplicaties.get(0);
//        }
//        return -1;
//    }

}
