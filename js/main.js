import { validateInterest, validateName, validatePhoneNo } from "./validate.js"

const d = document
const $ = d.querySelector.bind(d)

$('form').onsubmit = function(e){
  e.preventDefault()

  const fields = [
    {element : $('#user_name'), validator : validateName},
    {element : $('#user_phone'), validator : validatePhoneNo},
    {element : $('#user_birthday'), validator : (e, res = {})=>true}, // Ask teacher about it
    {element : $('#user_interests'), validator : validateInterest}
  ],
  query = new URLSearchParams

  let has_selected = false, is_complete = true

  for(const field of fields){
    const res = {}
    let input_tip = field.element.nextElementSibling

    if(!field.validator.call(field.element, field.element.value, res)){
      is_complete = false
      input_tip.innerText = res.msg

      if(!has_selected){
        field.element.focus()
        has_selected = true
      }

      field.element.addEventListener(
        'input', 
        ()=>input_tip.innerText = '', 
        {once : true}
      )
    }
    else{
      query.append(field.element.name, field.element.value)
    }
  }

  this.action+= '?' + query
  if(is_complete)
    this.submit()
}