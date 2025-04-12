<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <div>
    <h1>員工座位系統</h1>
    <SeatMap />
  </div>
</template>

<script>
import SeatMap from './components/SeatMap.vue'

export default {
  name: 'App',
  components: {
    SeatMap
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
