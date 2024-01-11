# Starting local instance of mongo and mongo-express
<pre> sudo docker run --network some-network --name mongo -p27017:27017 -d mongo:latest </pre>
<pre> sudo docker run --network some-network -p 8081:8081 mongo-express </pre>
