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


const hemicycleSeatCoordsBySeatNumber = reactive<{
  data: _.Dictionary<HemycicleSeatCoords>
  isLoading: boolean
}>({
  data: {},
  isLoading: true
});

function getHemicyleCoords() {
  const widthConeAngle = 15
  const widthAlleyAngle = 2
  const betweenAlleysAngleExtremCaseBig = 10
  const rowDelta = 10
  const seatsForRow = [3, 3, 4, 5, 6, 7, 7, 9, 9, 11, 11]
  const seatsForRowExtremCaseBig = [0, 0, 0, 0, 0, 0, 4, 4, 4, 5, 5, 3]
  const seatsForRowExtremCaseSmall = [0, 0, 0, 0, 0, 0, 2, 2, 3, 3, 3, 4]
  const seatsToSKip = [4, 6, 8]
  let seatNumber = 1
  let seatIdxInRow = 1
  let coneIdx = 0
  let rowIdx = 1
  let caseToApply = "default"


  const hemicyleSeatsCoords: HemycicleSeatCoords[] = []
  while (seatNumber <= 650) {
    if (seatsToSKip.includes(seatNumber)) {
      continue
    }
    switch (caseToApply) {
      case "extremRight":
        if (coneIdx === 0) {
          if (seatIdxInRow >= seatsForRowExtremCaseBig[rowIdx]) {
            coneIdx = 1
            seatIdxInRow = 0
          }
        }
        else {
          if (seatIdxInRow >= seatsForRowExtremCaseSmall[rowIdx]) {
            rowIdx++
            coneIdx = 0
            seatIdxInRow = 0
          }
        }
        if (rowIdx >= 13) {
          rowIdx = 0
          coneIdx++
          caseToApply = "default"
        }
      default:
        if (seatIdxInRow >= seatsForRow[rowIdx]) {
          rowIdx++
          seatIdxInRow = 0
        }

        if (rowIdx === 6 && coneIdx === 0) {
          coneIdx = 0
          caseToApply = "extremRight"
        }
        else if (rowIdx >= 13) {
          coneIdx++
          switch (coneIdx) {
            case 3:
            case 4:
            case 5:
              rowIdx = 2
              break;
            case 7:
              rowIdx = 1
              break;
            default:
              rowIdx = 0
          }
        }
    }

    let angleDeg
    switch (caseToApply) {
      case "extremRight":
        if (coneIdx === 0) {
          angleDeg = (betweenAlleysAngleExtremCaseBig - widthAlleyAngle / 2) / seatsForRowExtremCaseBig[rowIdx] * seatIdxInRow
        }
        else {
          angleDeg = betweenAlleysAngleExtremCaseBig + widthAlleyAngle / 2 + (widthConeAngle - betweenAlleysAngleExtremCaseBig - widthAlleyAngle / 2) / seatsForRowExtremCaseSmall[rowIdx] * seatIdxInRow
        }
        break;
      default:
        angleDeg = coneIdx * widthConeAngle + widthAlleyAngle / 2 + (widthConeAngle - widthAlleyAngle) / seatsForRow[rowIdx] * seatIdxInRow
    }
    const radius = rowIdx <= 5 ? rowIdx * rowDelta : (rowIdx + 1) * rowDelta

    hemicyleSeatsCoords.push(
      {
        seatNumber: seatNumber++,
        x: radius * Math.cos(angleDeg * Math.PI / 180),
        y: radius * Math.sin(angleDeg * Math.PI / 180)
      }
    )
  }
  return _.keyBy(hemicyleSeatsCoords, hemicyleSeatCoords => hemicyleSeatCoords.seatNumber)
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
