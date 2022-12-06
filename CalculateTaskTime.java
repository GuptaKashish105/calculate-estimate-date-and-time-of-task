import java.text.SimpleDateFormat;
import java.util.*;

public class CalculateTaskTime {

    public static class Leaves {
        Calendar leav;
        Date LeaveDate;

        public Calendar getLeav() {
            return leav;
        }

        Leaves(Calendar c) {
            this.leav = c;
            this.LeaveDate = c.getTime();
        }
    }

    public static Calendar getEndTime(Calendar startTime, Integer timeRequired, String workingHourStart,
            String workingHourEnd, List<Leaves> leaves) {

        Integer whStart, whEnd;
        Calendar preservedStartingDate = startTime;

        if (workingHourEnd.endsWith("AM")) {
            whEnd = Integer.parseInt(workingHourEnd.substring(0, workingHourEnd.length() - 2));
        } else {
            whEnd = Integer.parseInt(workingHourEnd.substring(0, workingHourEnd.length() - 2)) + 12;
        }

        if (workingHourStart.endsWith("AM")) {
            whStart = Integer.parseInt(workingHourStart.substring(0, workingHourStart.length() - 2));
        } else {
            whStart = Integer.parseInt(workingHourStart.substring(0, workingHourStart.length() - 2)) + 12;
        }

        Integer effHour = Math.abs(whEnd - whStart);

        Integer totalDaysToComplete = timeRequired / effHour;

        startTime.add(Calendar.DAY_OF_MONTH, totalDaysToComplete);

        // Calculate if leave comes between work day or not
        Date StartDate = preservedStartingDate.getTime();
        Date EndDate = startTime.getTime();

        for (Leaves leaves2 : leaves) {
            if (StartDate.after(leaves2.LeaveDate) && EndDate.before(leaves2.LeaveDate)) {
                startTime.add(Calendar.DAY_OF_MONTH, 1);
                EndDate = startTime.getTime();
            }
        }

        return startTime;
    }

    public static void main(String[] args) {
        try {
            // Staring time of Task
            Calendar startTime = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            startTime.setTime(sdf.parse("Tue Mar 06 16:02:37 GMT 2022"));

            // Other values
            Integer timeReq = 40;
            String StartingTime = "7AM";
            String EndingTime = "3PM";

            // Creating Leaves List
            ArrayList<Leaves> leav = new ArrayList<>();
            Calendar leav1 = Calendar.getInstance(), leav2 = Calendar.getInstance();
            leav1.setTime(sdf.parse("Fri Dec 09 16:02:37 GMT 2022"));
            leav1.setTime(sdf.parse("Fri Dec 23 16:02:37 GMT 2022"));
            Leaves l1 = new Leaves(leav1);
            Leaves l2 = new Leaves(leav2);
            leav.add(l1);
            leav.add(l2);

            // Calculating end time
            Calendar endDate = getEndTime(startTime, timeReq, StartingTime, EndingTime, leav);

            // Printing Output
            System.out.println("Task should be expected to end on : " + endDate.getTime());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}