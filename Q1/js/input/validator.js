/**
 * @typedef {object} ValidateResult
 * @property {string} message 
 * @property {boolean} result
 */

/**
 * Validate name if it contains valid characters `a-z`, `-`, and `'`
 * @returns {ValidateResult} result after validation
 */
function validateName(){
	const regexp = /^[a-z\'\-\ ]+$/gi, str = this.value.trim()
	let result = regexp.test(str), message = ''

	if(str === '')
		message = "Name field cannot be empty"

	else if(!result)
		message = "Name can only contain letters, spaces, hyphens, and apostrophes."

	return {
		message,
		result
	}
}

/**
 * Validate phone by ensuring it starts with `+xx`, `(+xx)`, `xx` with ` `, `-`, or `.`.
 * 
 * The available country codes are: 
 * 1. Singapore - `65`
 * 2. Australia - `61`
 * 3. Malaysia - `60`
 * 4. United Arab Emirates - `971`
 * @returns {ValidateResult} result after validation
 */
function validatePhone(){
	const regexp = /^(?<code>\(?\+?\d{1,3}\)?)\s(?<number>[\-\. \d]+)$/, str = this.value.trim()
	let match, result = false, message = ''

	if(str === ''){
		message = 'Phone field cannot be empty'
	}
	else if(match = str.match(regexp)){
		let { code, number } = match.groups

		code = code.replace(/\(|\)|\+/g, '')
		number = number.replace(/[^\d]+/g, '')

		switch(code){
			case '65' : 
				result = number.length === 8
				message = 'The numbers might be incorrect. Please check if there\'s 8 digits.'
				break
			case '61' : 
			case '971' :
				result = number.length === 9
				message = 'The numbers might be incorrect. Please check if there\'s 9 digits.'
				break
			case '60' : 
				result = number.length !== 8 || number.length !== 9
				message = 'The numbers might be incorrect. Please check if there\'s 8 to 9 digits.'
				break
			default :
				result = false
				message = 'The country code prefix is not supported. Only 60, 61, 65, and 971 are supported.'
		}
	}
	else{
		message = 'Phone number did not match the format'
		this.value = ''
	}

	return {
		message,
		result
	}
}

/**
 * Validate year input. Year cannot be more than or current year.
 * @returns {ValidateResult} result after validation
 */
function validateYear(){
	const current_year = datetime.years
	let message = '', result = false, str = this.value.trim()

	if(str === ''){
		message = 'Year cannot be empty.'
	}
	else if(!Number.isInteger(str * 1)){
		message = 'Year can only contain numbers.'
	}
	else if(str >= current_year){
		message = 'Oops! You can’t be born this year or in the future.'
	}
	else{
		result = true
	}

	return {
		message,
		result
	}
}

/**
 * Validate month input. Month is represented using `1` to `12`.
 * 
 * @returns {ValidateResult} result after validation
 */
function validateMonth(){
	let result = false, message = '', str = this.value.trim()

	if(str === ''){
		message = 'Month cannot be empty.'
	}
	else if(!Number.isInteger(str * 1)){
		message = 'Month can only contain numbers.'
	}
	else if(str < 1 || str > 12){
		message = 'Month field exceeds the range of valid month (1 to 12)'
	}
	else{
		result = true
	}

	return {
		message,
		result
	}
}

/**
 * Validate date input. Date is represented using `1` to `31`.
 * 
 * @returns {ValidateResult} result after validation
 */
function validateDate(){
	let result = false, message = '', str = this.value.trim()

	if(str === ''){
		message = 'Date cannot be empty.'
	}
	else if(!Number.isInteger(str * 1)){
		message = 'Date can only contain numbers.'
	}
	else if(str < 1 || str > 31){
		message = 'Date field exceeds the range of valid date (1 to 31)'
	}
	else{
		result = true
	}

	return {
		message,
		result
	}
}

/**
 * Validate whether the date is legitimate.
 * @param {string} y 
 * @param {string} m 
 * @param {string} d 
 */
function validateLegitDate(y, m, d){
	let message, result = false
	
	// We can concatenate together and see whether all the chars are numbers rather than separately calling String.match.
	if((d + m + y).match(/^\d+$/)){
		if(window.datetime.validDate(y, m, d)){
			result = true
		}
		else{
			message = `The Date of Birth does not exist. Maybe the month does not contain ${d}`
		}
	}
	else{
		message = "Unable to verify the date"
	}

	return {
		message,
		result
	}
}

/**
 * Validate interest by selecting a valid option
 * @returns {ValidateResult} result after validation
 */
function validateInterest(str){
	let result = false, message = ''

	if(this.selectedIndex === 0){
		message = 'Interest is not selected yet.'
	}
	else{
		result = true
	}

	return {
		message,
		result
	}
}