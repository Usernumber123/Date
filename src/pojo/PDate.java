package pojo;

public class PDate {
    private int year;
    private int month;
    private int day;
    public PDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDate iDate = (PDate) o;
        return year == iDate.year &&
                month == iDate.month &&
                day == iDate.day;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.year;
        result = 31 * result + this.day;
        result = 31 * result + this.month;
        return result;
    }
}
