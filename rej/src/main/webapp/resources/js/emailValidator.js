PrimeFaces.validator['custom.emailValidator'] = {
pattern: /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
validate: function(element, value) {
// use element.data() to access validation metadata, in this case there is none.
if(!this.pattern.test(value)) {
throw {
summary: 'Blad walidacji',
detail: value + ' nie jest poprawnym adresem email'
}
}
}
};