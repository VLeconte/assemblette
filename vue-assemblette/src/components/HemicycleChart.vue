<script setup lang="ts">
import type { HemicycleElement } from '@/entities/hemicycle-element';
import _ from 'lodash'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  CategoryScale,
  LinearScale,
  PointElement
} from 'chart.js'
import { computed, type PropType } from 'vue';
import { Scatter } from 'vue-chartjs'
import type { Authority } from '@/entities/authority';

const props = defineProps({
  hemicycleElements: {
    type: Object as PropType<HemicycleElement[]>,
    required: true
  },
  authorities: {
    type: Object as PropType<Authority[]>,
    required: true
  }
});

ChartJS.register(CategoryScale, LinearScale, PointElement, Title, Tooltip, Legend)

const radius = 1

// const data = computed(() => {
//   return {
//     datasets: [{
//       label: 'Scatter Dataset',
//       data: [{
//         x: radius * Math.cos(45 * Math.PI / 180),
//         y: radius * Math.sin(45 * Math.PI / 180)
//       }, {
//         x: 2 * Math.cos(45 * Math.PI / 180),
//         y: 2 * Math.sin(45 * Math.PI / 180)
//       }, {
//         x: radius * Math.cos(137 * Math.PI / 180),
//         y: radius * Math.sin(137 * Math.PI / 180)
//       }, {
//         x: 3 * Math.cos(137 * Math.PI / 180),
//         y: 3 * Math.sin(137 * Math.PI / 180)
//       }],
//       backgroundColor: 'rgb(255, 99, 132)'
//     }],
//   }
// })

const data = computed(() => {
  const hemicyleElementsByPGId = _.groupBy(props.hemicycleElements, hemicycleElement => hemicycleElement.authorityPG.id)
  return {
    datasets: _.map(hemicyleElementsByPGId, (value, key) => {
      const authoritiesById = _.keyBy(props.authorities, authority => authority.id)
      return {
        label: authoritiesById[key].label,
        backgroundColor: authoritiesById[key].associatedColor,
        data: _.map(value, (hemicycleElement) => {
          return {
            x: hemicycleElement.mandateAssembly.seatNumber / 10 * Math.cos(hemicycleElement.mandateAssembly.seatNumber % 100 * Math.PI / 180),
            y: hemicycleElement.mandateAssembly.seatNumber / 10 * Math.sin(hemicycleElement.mandateAssembly.seatNumber % 100 * Math.PI / 180)
          }
        })
      }
    })
  }
})

const options = {
  responsive: true,
  scales: {
    x: {
      min: -100,
      max: 100
    },
    y: {
      min: 0,
      max: 100
    }
  },
  plugins: {
    tooltip: {
      enabled: true
    }
  }
}



</script>

<template>
  <Scatter :data="data" :options="options" />
</template>
