<template>
  <v-container>
      <v-text-field
        v-model="dateRangeText"
        label="Date range"
        prepend-icon="mdi-calendar"
        readonly
        class="mb-n3"
       ></v-text-field>
      <v-date-picker
            full-width
            v-model="dates"
            range
            class="mb-5"
       ></v-date-picker>
      <v-autocomplete
        v-model="values"
        :items="items"
        filled
        chips
        small-chips
        multiple
        label="Sports"
        ></v-autocomplete>
        <v-btn 
          block
          color="primary"
          :loading="loading"
          :disabled="loading"
          @click="getSports"
          class="mb-5"
          >
          SUBMIT
        </v-btn>
        <v-card outlined>
          <v-list-item v-for="sport in sports" :key="sport">
            <v-list-item-content> {{ sport }} </v-list-item-content>
          </v-list-item>
        </v-card>

    </v-container>
</template>

<script>
  import axios from 'axios'


  export default {
    name: 'Home',
    data() {
      return {
        dates: [],
        formattedDates: ['', ''],
        items: [],
        values: [],
        loading: false,
        sports: []
      }
    },

    created() {
      axios.get('/api/sports')
        .then((response) => {
          var sportList = response.data;
          for (let sport of sportList)
            this.items.push(sport.name);
        }).catch((error) => {
          console.log(error);
        });
    },

    computed: {
      dateRangeText: function() {
        return this.dates.join(' ~ ');
      }
    },

    watch: {
      dates: function() {
        this.formatDate(this.dates);
      }
    },

    methods: {
      formatDate(dates) {
        if (!dates[0] || !dates[1]) return null;

        var [year, month, day] = dates[0].split('-');
        this.formattedDates[0] = `${day}-${month}-${year}`;
        [year, month, day] = dates[1].split('-');
        this.formattedDates[1] = `${day}-${month}-${year}`;
      },

      async getSports() {
        this.loading = true;

        axios.get("/api/locations/sorted", {
          params: {
            sports: this.values.join(',')
          }
        })
            .then((response) => {
              this.sports = response.data;
            }).catch((error) => {
              console.log(error);
            }).finally(() => {
              this.loading = false;
            })
        }
    }
  }
</script>
