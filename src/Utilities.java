public class Utilities {
    public static int getMinimumPosition(double[] list) {
        int position = 0;
        double minimum = list[0];

        for (int i = 0; i < list.length; i++)
            if (list[i] < minimum) {
                position = i;
                minimum = list[i];
            }

        return position;
    }
}