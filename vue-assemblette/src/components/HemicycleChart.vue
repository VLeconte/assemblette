<script setup lang="ts">
import type { HemicycleElement } from '@/entities/hemicycle-element';
import type { HemycicleSeatCoords } from '@/entities/hemicycle-seat-coords';
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
import { computed, onMounted, reactive, type PropType } from 'vue';
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
  console.log(hemicycleSeatCoordsBySeatNumber)
  const testHemicycleElements = _.filter(props.hemicycleElements, (hemicyleElement) => { return hemicyleElement.mandateAssembly.seatNumber >= 0 && hemicyleElement.mandateAssembly.seatNumber < 23 })
  const hemicyleElementsByPGId = _.groupBy(testHemicycleElements, hemicycleElement => hemicycleElement.authorityPG.id)
  return {
    datasets: _.map(hemicyleElementsByPGId, (value, key) => {
      const authoritiesById = _.keyBy(props.authorities, authority => authority.id)
      return {
        label: authoritiesById[key].label,
        backgroundColor: authoritiesById[key].associatedColor,
        data: _.map(value, (hemicycleElement) => {
          return {
            x: hemicycleSeatCoordsBySeatNumber.data[hemicycleElement.mandateAssembly.seatNumber].x,
            y: hemicycleSeatCoordsBySeatNumber.data[hemicycleElement.mandateAssembly.seatNumber].y
          }
        })
      }
    })
  }
})

const hemicylePlacementInstructions = [
  "row",
  "3",
  "skip",
  "3",
  "4",
  "5",
  "6"
]

const hemicycleSeatCoordsBySeatNumber = reactive<{
  data: _.Dictionary<HemycicleSeatCoords>
  isLoading: boolean
}>({
  data: {},
  isLoading: true
});

function getHemicyleCoords(hemicylePlacementInstructions: string[]) {
  const alleyAngleDelta = 15
  const rowDelta = 10
  let seatNumber = 1
  let alleyNumber = 0
  let rowNumber = 0

  const hemicyleSeatsCoords: HemycicleSeatCoords[] = []
  for (const hemicylePlacementInstruction in hemicylePlacementInstructions) {
    switch (hemicylePlacementInstruction) {
      case "alley":
        alleyNumber++
        break;
      case "row":
        rowNumber++
        break;
      case "skip":
        console.log(seatNumber)
        seatNumber++
        console.log(seatNumber)
        break;
      default:
        const numberOfSeatsToPlace = parseInt(hemicylePlacementInstruction)
        for (let i = 0; i < numberOfSeatsToPlace; i++) {
          const angleDeg = alleyNumber * alleyAngleDelta + 1 + (alleyAngleDelta - 2) / numberOfSeatsToPlace * i
          hemicyleSeatsCoords.push(
            {
              seatNumber: seatNumber++,
              x: rowNumber * rowDelta * Math.sin(angleDeg * Math.PI / 180),
              y: rowNumber * rowDelta * Math.cos(angleDeg * Math.PI / 180)
            }
          )
        }
        rowNumber++
        break;
    }
  }
  return _.keyBy(hemicyleSeatsCoords, hemicyleSeatCoords => hemicyleSeatCoords.seatNumber)
}

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

onMounted(() => {
  hemicycleSeatCoordsBySeatNumber.data = getHemicyleCoords(hemicylePlacementInstructions)
  hemicycleSeatCoordsBySeatNumber.isLoading = false
}
)


</script>

<template>
  <Scatter v-if="!hemicycleSeatCoordsBySeatNumber.isLoading" :data="data" :options="options" />
</template>
