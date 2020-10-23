import pojo.PDate;

public interface IMyDate {

    public boolean isLeapYear(int year);

    public boolean isValidDate(PDate date);

    public int getDayOfWeek(PDate date);

    public String toString(PDate date);

    public int countDays(PDate date);


}
