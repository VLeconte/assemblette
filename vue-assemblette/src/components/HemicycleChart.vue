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

// const data = computed(() => {
//   const hemicyleElementsByPGId = _.groupBy(props.hemicycleElements, hemicycleElement => hemicycleElement.authorityPG.id)
//   return {
//     datasets: _.map(hemicyleElementsByPGId, (value, key) => {
//       const authoritiesById = _.keyBy(props.authorities, authority => authority.id)
//       return {
//         label: authoritiesById[key].label,
//         backgroundColor: authoritiesById[key].associatedColor,
//         data: _.map(value, (hemicycleElement) => {
//           return {
//             x: hemicycleSeatCoordsBySeatNumber.data[hemicycleElement.mandateAssembly.seatNumber].x,
//             y: hemicycleSeatCoordsBySeatNumber.data[hemicycleElement.mandateAssembly.seatNumber].y
//           }
//         })
//       }
//     })
//   }
// })

const data = computed(() => {
  return {
    datasets:
      [{
        label: "Test hemycicle",
        backgroundColor: "#d916b3",
        data: _.map(hemicycleSeatCoordsBySeatNumber.data, (value, key) => {
          return {
            x: value.x,
            y: value.y

          }
        })
      }]
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
      min: -10,
      max: 80
    }
  },
  plugins: {
    tooltip: {
      enabled: true
    }
  }
}


const hemicycleSeatCoordsBySeatNumber = reactive<{
  data: _.Dictionary<HemycicleSeatCoords>
  isLoading: boolean
}>({
  data: {},
  isLoading: true
});

function getHemicyleCoords() {
  const widthFullHemicycle = 180
  const widthAlleyAngle = 8
  const widthSmallExtremConeAngle = 5  // From alley to alley
  const rowDelta = 4
  const rowOffset = 15
  const seatsForRow = [3, 3, 3, 4, 5, 6, 7, 7, 9, 9, 11, 11]
  const seatsForRowExtremCaseBig = [0, 0, 0, 0, 0, 0, 4, 4, 4, 5, 5, 3]
  const seatsForRowExtremCaseSmall = [0, 0, 0, 0, 0, 0, 2, 2, 3, 3, 3, 4]
  const seatsToSKip = [4, 29, 34, 37, 42, 55, 61, 65, 69]
  const widthConeAngle = widthFullHemicycle / 8  // From alley to alley
  const offsetAngle = (widthFullHemicycle - 180) / 2
  let seatNumber = 0
  let seatIdxInRow = -1
  let coneIdx = 0
  let rowIdx = 1
  let caseToApply = "default"


  const hemicyleSeatsCoords: HemycicleSeatCoords[] = []
  while (seatNumber <= 250) {
    seatNumber++
    if (seatsToSKip.includes(seatNumber)) {
      continue
    }

    seatIdxInRow++
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
          if (rowIdx >= 12) {
            rowIdx = 0
            coneIdx = 1
            caseToApply = "default"
          }
        }
        break;

      case "extremLeft":
        if (coneIdx === 0) {
          if (seatIdxInRow >= seatsForRowExtremCaseSmall[rowIdx]) {
            coneIdx = 1
            seatIdxInRow = 0
          }
        }
        else {
          if (seatIdxInRow >= seatsForRowExtremCaseBig[rowIdx]) {
            rowIdx++
            coneIdx = 0
            seatIdxInRow = 0
          }
          if (rowIdx >= 12) {
            rowIdx = 0
            coneIdx = 8
            caseToApply = "default"
          }
        }
        break;

      default:
        if (seatIdxInRow >= seatsForRow[rowIdx]) {
          rowIdx++
          seatIdxInRow = 0
        }

        if (rowIdx === 6 && coneIdx === 0) {
          coneIdx = 0
          caseToApply = "extremRight"
        }
        else if (rowIdx === 6 && coneIdx === 7) {
          coneIdx = 0
          caseToApply = "extremLeft"
        }
        else if (rowIdx >= 12) {
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
        break;
    }

    let angleDeg
    switch (caseToApply) {
      case "extremRight":
        if (coneIdx === 0) {
          angleDeg = (
            (widthConeAngle - widthSmallExtremConeAngle - widthAlleyAngle / 2) / (seatsForRowExtremCaseBig[rowIdx] - 1) * seatIdxInRow)
        }
        else {
          angleDeg = (
            widthConeAngle
            - widthSmallExtremConeAngle
            + widthAlleyAngle / 2
            + (widthSmallExtremConeAngle - widthAlleyAngle) / (seatsForRowExtremCaseSmall[rowIdx] - 1) * seatIdxInRow)
        }
        break;
      default:
        angleDeg = (
          coneIdx * widthConeAngle
          + widthAlleyAngle / 2
          + (widthConeAngle - widthAlleyAngle) / (seatsForRow[rowIdx] - 1) * seatIdxInRow)
    }
    angleDeg -= offsetAngle
    const radius = rowIdx <= 5 ? rowOffset + rowIdx * rowDelta : rowOffset + (rowIdx + 1) * rowDelta

    hemicyleSeatsCoords.push(
      {
        seatNumber: seatNumber,
        x: radius * Math.cos(angleDeg * Math.PI / 180),
        y: radius * Math.sin(angleDeg * Math.PI / 180)
      }
    )
  }
  return _.keyBy(hemicyleSeatsCoords, hemicyleSeatCoords => hemicyleSeatCoords.seatNumber)
}


onMounted(() => {
  hemicycleSeatCoordsBySeatNumber.data = getHemicyleCoords()
  hemicycleSeatCoordsBySeatNumber.isLoading = false
}
)


</script>

<template>
  <Scatter v-if="!hemicycleSeatCoordsBySeatNumber.isLoading" :data="data" :options="options" />
</template>
