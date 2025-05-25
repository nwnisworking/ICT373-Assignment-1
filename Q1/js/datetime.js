/**
 * Checks whether the stated year is a leap year
 * @param {number} year Year for verifying leap year
 */
export function isLeapYear(year){
	if(year % 4 == 0){
		if(year % 100 == 0){
			return year % 400 == 0
		}

		return true
	}

	return false
}

/**
 * Checks whether the date is valid
 * @param {number} year year to validate
 * @param {number} month month to validate
 * @param {number} date date to validate
 */
export function validDate(year, month, date){
	if(month < 1 || month > 12) return false

	let days_in_month = 0

	if(month == 2){
		days_in_month = isLeapYear(year) ? 29 : 28
	} 
	else if(month <= 7){
		days_in_month = month % 2 === 1 ? 31 : 30
	}
	else{
		days_in_month = month % 2 === 0 ? 31 : 30
	}

	return date >= 1 && date <= days_in_month
}

const TIME = performance.timeOrigin 
const TOTAL_SECONDS = TIME / 1000
const TOTAL_DAYS = TOTAL_SECONDS / (24 * 60 * 60)

const temp = {
	hours : Math.floor(TOTAL_SECONDS / 60 / 60 % 24),
	minutes : Math.floor(TOTAL_SECONDS / 60 % 60),
	seconds : Math.floor(TOTAL_SECONDS % 60),
	years : 1970,
	months : 0,
	date : 0
}

let days = TOTAL_DAYS

while(true){
	const is_leap_year = isLeapYear(temp.years)
	const days_in_year = is_leap_year ? 366 : 365

	if(days - days_in_year > 0){
		days-= days_in_year
		temp.years++
	}
	else{
		for(let i = 1; i <= 12; i++){
			let days_in_month = 0
			
			if(i == 2){
				days_in_month = is_leap_year ? 29 : 28
			} 
			else if(i <= 7){
				days_in_month = i % 2 === 1 ? 31 : 30
			}
			else{
				days_in_month = i % 2 === 0 ? 31 : 30
			} 

			if(days - days_in_month > 0){
				 days-= days_in_month
			}
			else{
				temp.months = i
				temp.date = Math.floor(days + 1)
				break
			}
		}

		break
	}
}

export default Object.freeze(temp)