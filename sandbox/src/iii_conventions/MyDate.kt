package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        var result: Int = year - other.year
        if (result != 0) return result

        result = month - other.month
        if (result != 0) return result

        result = dayOfMonth - other.dayOfMonth
        return result
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = this.addTimeIntervals(timeInterval, 1)

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate = this.addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.numberOfRepetitions)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val numberOfRepetitions: Int)

operator fun TimeInterval.times(numberOfRepetitions: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, numberOfRepetitions)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        private var currentDate: MyDate = start

        override fun hasNext(): Boolean = currentDate <= endInclusive

        override fun next(): MyDate {
            val result = currentDate
            currentDate = currentDate.nextDay()
            return result
        }
    }
}

operator fun DateRange.contains(date: MyDate): Boolean {
    return start < date && date <= endInclusive
}
