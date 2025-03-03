import './assets/main.css'
import 'primeicons/primeicons.css'
import PrimeVue from 'primevue/config'
import Aura from '@primeuix/themes/aura'
import { definePreset } from '@primeuix/themes'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import colors from 'tailwindcss/colors'

const pinia = createPinia()
const app = createApp(App)

const AssemblettePreset = definePreset(Aura, {
  components: {
    paginator: {
      navButton: {
        selectedBackground: colors.green[700],
        selectedColor: colors.gray[50],
      },
    },
  },
})

app.use(router)
app.use(pinia)
app.use(PrimeVue, {
  theme: {
    preset: AssemblettePreset,
  },
})

app.mount('#app')
