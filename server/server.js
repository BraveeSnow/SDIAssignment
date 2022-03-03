const express = require('express')
const app = express()
const port = 80

app.get('/', (req, res) => {
	res.set('Connection', 'close')
	res.send('GET request received!')
})

app.post('/', (req, res) => {
	res.set('Connection', 'close')
	res.send('POST request received!')
})

app.put('/', (req, res) => {
	res.set('Connection', 'close')
	res.send('PUT request received!')
})

app.delete('/', (req, res) => {
	res.set('Connection', 'close')
	res.send('DELETE request received!')
})


let srv = app.listen(port, () => {
	console.log(`On port ${port}`)
})
