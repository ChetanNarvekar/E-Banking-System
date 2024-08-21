import axios from 'axios'
import config from '../config'



export async function register(firstName,middleName, lastName, email,password,phoneNumber,dateOfBirth,gender,adharNo,panNo,tpin,accountType) {
  // body parameters
  const body = {
    firstName,
    middleName,
    lastName,
    email,
    password,
    phoneNumber,
    dateOfBirth,
    gender,
    adharNo,
    panNo,
    tpin,
    accountType
  }

  // make API call
  const response = await axios.post(`${config.url}/bank/customers`, body,{
    headers: {
        'Content-Type': 'application/json'
      }
    });
  
  // read JSON data (response)
  return response.data
}

export async function login(email, password) {
  // body parameters
  const body = {
    email,
    password,
  }

  // make API call
  const response = await axios.post(`${config.url}/bank/customers`, body)

  // read JSON data (response)
  return response.data
}
