<template>
  <v-app>
    <!-- <v-app-bar app>

      <v-tabs>
        <v-tab to="/">
          Home
        </v-tab>
        <v-tab to="/addlist">
          Add List
        </v-tab>
      </v-tabs>

      <v-spacer></v-spacer>

      <v-btn
        href="https://github.com/vuetifyjs/vuetify/releases/latest"
        target="_blank"
        text
      >
        <span class="mr-2">Latest Release</span>
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
    </v-app-bar> -->

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    //
  }),
};
</script>
