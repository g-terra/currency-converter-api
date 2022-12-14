<h3>Currency Converter (Coding Test - Software Engineer)</h3>

This exercise focuses on functionality, "clear code" and how well the application is tested.

Imagine you are part of the engineering team at AWIN which is responsible for Finance topics
(e.g. invoicing advertisers and paying publishers. For the sake of this exercise, let's assume that AWIN only operates
with Euro as a currency within the EU, we don't - but let's assume).

There are plans to expand into additional markets outside of the EUR zone. For this, your team has received a request to
build a small helper app to perform currency exchange rate conversions. This is the starting point for adding currency
conversion functionality to the entire platform later on and allowing the business to expand into new regions.

Your task is to complete a concept for a currency conversion solution and to implement a small but key part of the
application including some tests.

For getting actual the conversion rates, assume you would use this service: https://exchangerate.host/
(request sample: `https://api.exchangerate.host/latest?base=EUR&symbols=AUD,CAD,CHF,CNY,GBP,JPY,USD`)

Tasks:
 * Implement 'CurrencyExchangeRateService' (please use this https://exchangerate.host as back-end service)
 * Write at least on test case (or as many as you think necessary)
 * Please introduce any change to any existing class if you think it improves the solution


Good luck!

# DEVELOPER NOTES

For testing, feel free to use http://localhost:8080/swagger-ui/index.html#/

For verifying the development of the solutions, refer to https://github.com/g-terra/currency-converter-api

## Possible Features that should be added on the future

- average rating to a daily level
- fall back service in case of main exchange provider is down


