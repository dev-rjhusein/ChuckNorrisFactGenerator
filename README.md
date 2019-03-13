# ChuckNorrisFactGenerator
Console program that makes REST calls to <b>chucknorris.io</b>
<h5>Jokes/Facts returned are supplied by chucknorris.io and CAN be inappropriate and/or pretty lame</h5>

Dependencies:
<ol>
  <li>Java 8+</li>
  <li>Google's JSON Simple Library v-1.1.1 <a href="https://storage.googleapis.com/google-code-archive-     downloads/v2/code.google.com/json-simple/json-simple-1.1.1.jar"> Download it here </a></li>
</ol>

When the program executes, you'll be prompted for integer input (1-10). This is the number of jokes/facts you want returned.
The program will then construct that number of threads and make a REST request with each. The result is consumed into a Simple JSON Object and displayed to the console. A CountDownLatch allows the main thread to wait for all the REST threads to finish executing, before looping back and getting more input.

