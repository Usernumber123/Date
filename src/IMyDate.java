public interface IMyDate {

    public boolean isLeapYear(int year);

    public boolean isValidDate(int year, int month, int day);

    public int getDayOfWeek(int year, int month, int day);

    public String toString(int year, int month, int day);

    public int countDays(int year, int month, int day);
}
