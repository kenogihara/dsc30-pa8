/**
 * PA8 Part 1 worksheet (style not required)
 */
public class CollisionHandling {

    /**
     * method that stores the result of Q1 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] linearProbingResult(){
        /*
        TODO: create the values for the hashtable representations
         */
        int[][] output = {{0, 24, 5, 42, 0, 28, 84, 98}, {11, 24, 5, 42, 77, 28, 84, 98}}; //change
        return output;
    }

    /**
     * method that stores the result of Q2 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] quadraticProbingResult(){
        /*
        TODO: create the values for the hashtable representations
         */
        int[][] output = {{101, 0, 0, 16, 0, 26, 11, 0}, {101, 2, 0, 16, 33, 26, 11, 0, 9, 0, 0, 0, 0, 201, 0, 0}}; //change
        return output;
    }

    /**
     * method that stores the result of Q3 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] doubleHashingResult(){
        /*
        TODO: create the values for the hashtable representations
         */
        int[][] output = {{0, 107, 100, 0, 12, 3, 0, 0}, {41, 107, 100, 39, 12, 3, 0, 51, 0, 0, 0, 0, 0, 51, 0, 0}}; //change
        return output;
    }

}