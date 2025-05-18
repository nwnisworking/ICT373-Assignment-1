/**
 * Validate name if it contains valid characters `a-z`, `-`, and `'`
 * @param {string} str value to validate 
 * @param {object} result additional information of the error
 * @returns {boolean} result after validation
 */
export function validateName(str, res = {}){
  if((/^[a-z\'\-\ ]+$/gi).test(str)){
    res['msg'] = ''
    return true
  }
  else{
    res['msg'] = 'Name can only contain letters, spaces, hyphens, and apostrophes.'
    return false
  }
}

/**
 * Validate phone by ensuring it starts with `+65`, `(+65)`, `65` with ` `, `-`, or `.`
 * @param {string} str value to validate 
 * @param {object} result additional information of the error
 * @returns {boolean} result after validation
 */
export function validatePhoneNo(str, result = {}){
  const match = str.match(/^(?<country_code>\(\+\d{1,3}\)|\+\d{1,3}|\d{1,3}) (?<number>[-. \d]+)$/)
  let msg = ''
  
  if(match !== null){
    let { country_code, number } = match.groups
    
    country_code = country_code.replace(/\(|\)|\+/g, '')
    number = number.replace(/[^\d]+/g, '')
  
    switch(country_code){
      case '65' : 
        if(number.length !== 8)
          msg = 'The numbers might be incorrect. Please check if there\'s 8 digits.'
        break
      case '971' : 
      case '61' :
        if(number.length !== 9)
          msg = 'The numbers might be incorrect. Please check if there\'s 9 digits.'
        break
      case '60' : 
        if(number.length < 9 || number.length > 10)
          msg = 'The numbers might be incorrect. Please check if there\'s 9 to 10 digits.'
        break
      default : 
        msg = "The country code is invalid. Only (+60), (+61), (+65), and (+971) are supported."
    }
  }
  else
    msg = 'Add a space between the country code and the phone number so it’s easier to read and validate!.'

  if(typeof result === 'object' && !Array.isArray(result)) 
    result['msg'] = msg

  return msg.length === 0
}

/**
 * Validate interest by selecting on a valid option
 * @param {string} str value to validate 
 * @param {object} result additional information of the error
 * @returns {boolean} result after validation
 */
export function validateInterest(str, result = {}){
  result['msg'] = ''

  if(this.selectedIndex === 0){
    result['msg'] = "You have yet to select your interest."
    return false
  }

  return true
}