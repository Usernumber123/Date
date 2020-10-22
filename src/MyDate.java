import pojo.PDate;

public class MyDate implements IMyDate {
    private int nowDay;
    private int nowYear;
    private int nowMonth;


    enum DayOfWeek {
        SATURDAY("Sunday"),
        SUNDAY("Wednesday"),
        MONDAY("Mondey"),
        TUESDAY("Tuesday"),
        WEDNESDAY("Saturday"),
        THURSDAY("Thursday"),
        FRIDAY("Friday");
        private String title;

        DayOfWeek(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    enum Month {
        JAN("Jan"),
        FEB("Feb"),
        MAR("Mar"),
        APR("Apr"),
        MAY("May"),
        JUN("Jun"),
        JUL("Jul"),
        AUG("Aug"),
        SEP("Sep"),
        OCT("Oct"),
        NOV("Nov"),
        DEC("Dec");
        private String title;

        Month(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    @Override
    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && ((year % 100 != 0) || (year % 100 == 0 && year % 400 == 0)));
    }

    @Override
    public boolean isValidDate(int year, int month, int day) {
        if ((month >= 1 && month <= 12) && (day >= 1 && day <= 31) && year > 0) {
            if (isLeapYear(year) && month == 2) {
                return day <= 29;
            } else if (day <= (28 + (month + Math.floor(month / 8)) % 2 + 2 % month + 2 * Math.floor(1 / month))) {
                return true;
            } else return false;
        }
        return false;
    }


    @Override
    public int getDayOfWeek(int year, int month, int day) {
        if (isValidDate(year, month, day)) {
            int codeMonth = 0;
            if (month == 1 || month == 10) codeMonth = 1;
            if (month == 5) codeMonth = 2;
            if (month == 8) codeMonth = 3;
            if (month == 2 || month == 11 || month == 3) codeMonth = 4;
            if (month == 6) codeMonth = 5;
            if (month == 9 || month == 12) codeMonth = 6;
            if (month == 4 || month == 7) codeMonth = 0;
            int century = 0;
            if ((int) (year / 100) % 4 == 0) century = 6;
            if ((int) (year / 100) % 4 == 1) century = 4;
            if ((int) (year / 100) % 4 == 2) century = 2;
            if ((int) (year / 100) % 4 == 3) century = 0;
            int codeYear = (century + year % 100 + (int) (year % 100 / 4)) % 7;
            int dayOfWeek = (day + codeMonth + codeYear) % 7;
            if (isLeapYear(year) && month <= 2) dayOfWeek -= 1;
            return dayOfWeek;
        } else throw new IllegalArgumentException();
    }

    @Override
    public String toString(int year, int month, int day) {
        return DayOfWeek.values()[getDayOfWeek(year, month, day)].getTitle() + " " + day + " " + Month.values()[month - 1].getTitle() + " " + year;
    }

    public void today() {
        long timeCounter = 0;
        int yearStart = 1970;
        int monthStart = 1;
        this.nowYear = 1970;
        this.nowMonth = 0;
        this.nowDay = 0;
        long nowTime = System.currentTimeMillis() / 1000;
        boolean year1 = true;
        boolean mon = true;
        while (timeCounter <= nowTime) {
            if (!isLeapYear(yearStart)) {
                timeCounter += 31536000;//add to time one year in seconds
                if (timeCounter > nowTime) {
                    timeCounter -= 31536000;
                    year1 = false;
                }
            } else {
                timeCounter += 31622400;//add to time one leap year in seconds
                if (timeCounter > nowTime) {
                    timeCounter -= 31622400;
                    year1 = false;
                }
            }
            if (!year1 && mon) {
                if (isLeapYear(yearStart) && monthStart == 2) {
                    timeCounter += 86400 * 29;//add to time one month in seconds
                    if (timeCounter > nowTime) {
                        timeCounter -= 86400 * 29;
                        mon = false;
                    }
                } else {
                    timeCounter += 86400 * ((28 + (monthStart + Math.floor(monthStart / 8)) % 2 + 2 % monthStart + 2 * Math.floor(1 / monthStart)));
                    if (timeCounter > nowTime) {
                        timeCounter -= 86400 * ((28 + (monthStart + Math.floor(monthStart / 8)) % 2 + 2 % monthStart + 2 * Math.floor(1 / monthStart)));
                        mon = false;
                    }
                }
                monthStart++;
                nowMonth++;
            }
            if (!mon) {
                timeCounter += 86400;//add to time one day in seconds
                nowDay++;
            }
            if (year1) {
                yearStart++;
                nowYear++;
            }
        }
    }

    @Override
    public int countDays(int year, int month, int day) {
        if (isValidDate(year, month, day)) {
            today();
            int yearClone = year;
            int days = 0;
            if (nowYear > year) {
                while (yearClone != nowYear) {
                    if (isLeapYear(yearClone)) days += 366;
                    else days += 365;
                    yearClone++;
                }
                int daysMonth = 0;
                for (int i = 1; i < month; i++) {
                    if (isLeapYear(year) && i == 2) {
                        daysMonth += 29;
                    } else daysMonth += ((28 + (i + Math.floor(i / 8)) % 2 + 2 % i + 2 * Math.floor(1 / i)));
                }

                int daysNowMonth = 0;
                for (int i = 1; i < nowMonth; i++) {
                    if (isLeapYear(nowYear) && i == 2) {
                        daysNowMonth += 29;
                    } else daysNowMonth += ((28 + (i + Math.floor(i / 8)) % 2 + 2 % i + 2 * Math.floor(1 / i)));
                }
                days = days - daysMonth - day + daysNowMonth + nowDay;
            } else {
                while (yearClone != nowYear) {
                    if (isLeapYear(yearClone)) days += 366;
                    else days += 365;
                    yearClone--;
                }
                int daysMonth = 0;
                for (int i = month; i < 12; i++) {
                    if (isLeapYear(year) && i == 2) {
                        daysMonth += 29;
                    } else daysMonth += ((28 + (i + Math.floor(i / 8)) % 2 + 2 % i + 2 * Math.floor(1 / i)));
                }
                int daysNowMonth = 0;
                for (int i = nowMonth; i < 12; i++) {
                    if (isLeapYear(nowYear) && i == 2) {
                        daysNowMonth += 29;
                    } else daysNowMonth += ((28 + (i + Math.floor(i / 8)) % 2 + 2 % i + 2 * Math.floor(1 / i)));
                }
                days = days - daysMonth - day + daysNowMonth + nowDay;
            }


            return days;
        } else throw new IllegalArgumentException();
    }
    public boolean isValidDate(PDate date){
       return isValidDate(date.getYear(),date.getMonth(),date.getDay());
    }

    public int getDayOfWeek(PDate date){
        return getDayOfWeek(date.getYear(),date.getMonth(),date.getDay());
    }

    public String toString(PDate date){
        return toString(date.getYear(),date.getMonth(),date.getDay());
    }

    public int countDays(PDate date){
        return countDays(date.getYear(),date.getMonth(),date.getDay());
    }
}
