$('form').onsubmit = function(e){
	e.preventDefault() 

	const fields = [
		{element : _('user_name'), validator : validateName},
		{element : _('user_phone'), validator : validatePhone},
		{element : _('user_dob_date'), validator : function(){
			let data = validateDate.call(this)
			const year = _('user_dob_year')?.value
			const month = _('user_dob_month')?.value
			const date = this?.value

			if(!data.result)
				return data

			if(month && year.match(/^\d+$/g))
				return validateLegitDate(year, month, date)
			else // Waits until year and month are filled to validate the date. Otherwise, result is positive!
				return {message : '', result : true}
		}},
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
		this.action+= `?${search_param}`
		this.submit()
	}
}