import { $, _ } from './utils.js'
import { 
	validateDate, 
	validateInterest, 
	validateMonth, 
	validateName, 
	validatePhone, 
	validateYear
} from './input/validator.js'
import { validDate } from './datetime.js'

$('form').onsubmit = function(e){
	e.preventDefault() 

	const fields = [
		{element : _('user_name'), validator : validateName},
		{element : _('user_phone'), validator : validatePhone},
		{element : _('user_dob_date'), validator : validateDate},
		{element : _('user_dob_month'), validator : validateMonth},
		{element : _('user_dob_year'), validator : validateYear},
		{element : _('user_interests'), validator : validateInterest}
	],
	search_param = new URLSearchParams

	let is_valid = true
	let has_selected = false

	for(const {element, validator} of fields){
		const { result, message } = validator.call(element)
		const input_tip = element.parentElement.querySelector('small')

		if(!element.oninput){
			element.oninput = ()=>input_tip.innerText = ''
		}

		if(!result){
			is_valid = false

			if(!input_tip.innerText)
				input_tip.innerText = message

			if(!has_selected){
				element.focus()
				has_selected = true
			}
		}
		else{
			search_param.append(element.name, element.value)
		}
	}

	if(is_valid){
		// This special validation is required for checking whether DOB is valid
		// before submitting to the backend for further processing 
		const year = _('user_dob_year')
		const month = _('user_dob_month')
		const date = _('user_dob_date')
		
		if(!validDate(year.value, month.value, date.value)){
			month.focus()
			year.parentElement.querySelector('small').innerText = `Date of Birth does not exist. Check whether the month contains ${date.value}.`
			return
		}

		this.action+= `?${search_param}`
		this.submit()
	}
}