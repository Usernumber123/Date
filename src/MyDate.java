public class MyDate {

    boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    return true;
                } else return false;
            } else return true;
        } else return false;
    }

    boolean isValidDate(int year, int month, int day) {
        if ((month >= 1 && month <= 12) && (day >= 1 && day <= 31) && year > 0) {
            if (isLeapYear(year) && month == 2) {
                if (day <= 29) {
                    return true;
                } else {
                    return false;
                }
            } else if (day <= (28 + (month + Math.floor(month / 8)) % 2 + 2 % month + 2 * Math.floor(1 / month))) {
                return true;
            } else return false;
        }
        return false;
    }

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

    int getDayOfWeek(int year, int month, int day) {
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
        } else throw new Error();
    }

    String toString(int year, int month, int day) {
        return DayOfWeek.values()[getDayOfWeek(year, month, day)].getTitle() + " " + day + " " + Month.values()[month - 1].getTitle() + " " + year;
    }

    int countDays(int year, int month, int day) {
        if (isValidDate(year, month, day)) {
            long time1 = 0;
            int yearStart = 1970;
            int monthStart = 1;
            int nowYear = 1970;
            int nowMonth = 0;
            int nowDay = 0;
            long time2 = System.currentTimeMillis() / 1000;
            boolean year1 = true;
            boolean mon = true;
            while (time1 <= time2) {
                if (!isLeapYear(yearStart)) {
                    time1 += 31536000;
                    if (time1 > time2) {
                        time1 -= 31536000;
                        year1 = false;
                    }
                } else {
                    time1 += 31622400;
                    if (time1 > time2) {
                        time1 -= 31622400;
                        year1 = false;
                    }
                }
                if (!year1 && mon) {
                    if (isLeapYear(yearStart) && monthStart == 2) {
                        time1 += 86400 * ((28 + (monthStart + Math.floor(monthStart / 8)) % 2 + 2 % monthStart + 2 * Math.floor(1 / monthStart)) + 1);
                        if (time1 > time2) {
                            time1 -= 86400 * ((28 + (monthStart + Math.floor(monthStart / 8)) % 2 + 2 % monthStart + 2 * Math.floor(1 / monthStart)) + 1);
                            mon = false;
                        }
                    } else {
                        time1 += 86400 * ((28 + (monthStart + Math.floor(monthStart / 8)) % 2 + 2 % monthStart + 2 * Math.floor(1 / monthStart)));
                        if (time1 > time2) {
                            time1 -= 86400 * ((28 + (monthStart + Math.floor(monthStart / 8)) % 2 + 2 % monthStart + 2 * Math.floor(1 / monthStart)));
                            mon = false;
                        }
                    }
                    monthStart++;
                    nowMonth++;
                }
                if (!mon) {
                    time1 += 86400;
                    nowDay++;
                }
                if (year1) {
                    yearStart++;
                    nowYear++;
                }
            }
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

                //if(month)
                int daysNowMonth = 0;
                for (int i = 1; i < nowMonth; i++) {
                    if (isLeapYear(nowYear) && i == 2) {
                        daysNowMonth += 29;
                    } else daysNowMonth += ((28 + (i + Math.floor(i / 8)) % 2 + 2 % i + 2 * Math.floor(1 / i)));
                }
                System.out.println(daysNowMonth);
                System.out.println(daysMonth);
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
        } else throw new Error();
    }

}
