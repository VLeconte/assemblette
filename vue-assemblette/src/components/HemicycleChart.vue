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
  PointElement,
  type ChartDataset,
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

const data = computed(() => {
  const hemicycleSeatCoordsBySeatNumber = _.keyBy(hemicyleSeatsCoords.data, hemicyleSeatCoords => hemicyleSeatCoords.seatNumber)
  return {
    datasets: _.map(props.hemicycleElements, (hemicycleElement) => {
      const authoritiesById = _.keyBy(props.authorities, authority => authority.id)
      return {
        label: hemicycleElement.deputy.firstName + " " + hemicycleElement.deputy.lastName,
        backgroundColor: authoritiesById[hemicycleElement.authorityPG.id].associatedColor,
        data:
          [{
            x: hemicycleSeatCoordsBySeatNumber[hemicycleElement.mandateAssembly.seatNumber].x,
            y: hemicycleSeatCoordsBySeatNumber[hemicycleElement.mandateAssembly.seatNumber].y
          }]
      }
    })
  }
})

// const data = computed(() => {
//   const hemicycleSeatCoordsBySeatNumber = _.keyBy(hemicyleSeatsCoords.data, hemicyleSeatCoords => hemicyleSeatCoords.seatNumber)
//   return {
//     datasets: _.map(hemicycleSeatCoordsBySeatNumber, (value, key) => {
//       return {
//         label: key,
//         backgroundColor: "#d916b3",
//         data: [{
//           x: value.x,
//           y: value.y
//         }]
//       }
//     })
//   }
// }
// )

const chartHalfWidth = 63
const chartYMin = -8
const chartYMax = 63

const options = {
  responsive: true,
  scales: {
    x: {
      min: -chartHalfWidth,
      max: chartHalfWidth,
      display: false
    },
    y: {
      min: chartYMin,
      max: chartYMax,
      display: false
    }
  },
  aspectRatio: chartHalfWidth * 2 / (chartYMax - chartYMin),
  onResize: (chart: ChartJS, size: { width: number; height: number }) => {
    console.log(size)
    for (const dataset of chart.config.data.datasets as ChartDataset<'line'>[]) {
      dataset.pointRadius = size.height * 5 / 568
      dataset.pointHoverRadius = size.height * 10 / 568
    }
  },
  plugins: {
    legend: {
      display: false
    },
    tooltip: {
      enabled: true
    }
  },
  animation: true,
  animations: {
    x: {
      easing: 'linear',
      from: 1000,
      delay: 0,
      duration: 0
    },
    y: {
      easing: 'linear',
      from: 500,
      delay: 0,
      duration: 50000
    },
    // color: { type: 'color', properties: ['borderColor', 'backgroundColor'], from: 'transparent' }
  },
}




const hemicyleSeatsCoords = reactive<{
  data: HemycicleSeatCoords[]
  isLoading: boolean
}>({
  data: [],
  isLoading: true
});

function getHemicyleCoords() {
  const widthFullHemicycle = 188
  const widthAlleyAngle = 8
  const widthSmallExtremConeAngle = 12  // From center of alley to center of alley
  if (widthSmallExtremConeAngle <= widthAlleyAngle) {
    throw new Error("widthSmallExtremConeAngle must be superior to widthAlleyAngle");
  }
  const rowDelta = 3
  const rowOffset = 25
  const seatsForRowDefault = [3, 3, 4, 4, 5, 6, 7, 7, 9, 9, 11, 11]
  const seatsForRowExtremCase = [0, 3, 3, 4, 5, 6]
  const seatsForRowExtremCaseBig = [0, 0, 0, 0, 0, 0, 4, 4, 4, 5, 5, 3]
  const seatsForRowExtremCaseSmall = [0, 0, 0, 0, 0, 0, 2, 2, 3, 3, 3, 4]
  const seatsToSKip = [
    4, 29, 34, 37, 42, 46, 55, 61, 65, 69, 74,
    107, 115, 121, 131, 142, 159, 160, 161, 194,
    202, 208, 218, 229, 246, 247, 252, 275, 283, 289, 299,
    310, 316, 328, 355, 363, 369, 379, 390, 396,
    408, 435, 443, 449, 459, 470, 476, 477,
    521, 529, 535, 545, 556, 562, 563, 575, 579, 598,
    605, 608, 613, 617, 622, 631, 635, 641, 646, 647
  ]
  const widthConeAngle = widthFullHemicycle / 8  // From center of alley to center of alley
  const offsetAngle = (widthFullHemicycle - 180) / 2
  let seatNumber = 0
  let seatIdxInRow = -1
  let seatsForRow = seatsForRowExtremCase
  let coneIdx = 0
  let rowIdx = 1
  let caseToApply = "default"


  const hemicyleSeatsCoords: HemycicleSeatCoords[] = []
  while (seatNumber <= 649) {
    seatNumber++
    if (seatsToSKip.includes(seatNumber)) {
      continue
    }

    seatIdxInRow++
    switch (caseToApply) {
      case "extremRight":
        if (seatIdxInRow >= seatsForRow[rowIdx]) {
          seatIdxInRow = 0
          if (coneIdx === 0) {
            coneIdx = 1
            seatsForRow = seatsForRowExtremCaseSmall
          }
          else if (coneIdx === 1) {
            if (rowIdx >= 11) {
              rowIdx = 0
              coneIdx = 1
              seatsForRow = seatsForRowDefault
              caseToApply = "default"
            }
            else {
              rowIdx++
              coneIdx = 0
              seatsForRow = seatsForRowExtremCaseBig
            }
          }
        }
        break;

      case "extremLeft":
        if (seatIdxInRow >= seatsForRow[rowIdx]) {
          seatIdxInRow = 0
          if (coneIdx === 0) {
            coneIdx = 1
            seatsForRow = seatsForRowExtremCaseBig
          }
          else if (coneIdx === 1) {
            if (rowIdx >= 11) {
              rowIdx = 0
              coneIdx = 8
              seatsForRow = seatsForRowDefault
              caseToApply = "default"
            }
            else {
              rowIdx++
              coneIdx = 0
              seatsForRow = seatsForRowExtremCaseSmall
            }
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
          seatsForRow = seatsForRowExtremCaseBig
          caseToApply = "extremRight"
        }
        else if (rowIdx === 6 && coneIdx === 7) {
          coneIdx = 0
          seatsForRow = seatsForRowExtremCaseSmall
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
              seatsForRow = seatsForRowExtremCase
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
            (widthConeAngle - widthSmallExtremConeAngle - widthAlleyAngle / 2) / (seatsForRow[rowIdx] - 1) * seatIdxInRow)
          if (rowIdx === 11) {
            angleDeg = (
              (widthConeAngle - widthSmallExtremConeAngle - widthAlleyAngle / 2) / (seatsForRow[rowIdx] - 1 + 2) * (seatIdxInRow + 1))
          }
        }
        else {
          angleDeg = (
            widthConeAngle
            - widthSmallExtremConeAngle
            + widthAlleyAngle / 2
            + (widthSmallExtremConeAngle - widthAlleyAngle) / (seatsForRow[rowIdx] - 1) * seatIdxInRow)
        }
        break;
      case "extremLeft":
        if (coneIdx === 0) {
          angleDeg = (
            7 * widthConeAngle
            + widthAlleyAngle / 2
            + (widthSmallExtremConeAngle - widthAlleyAngle) / (seatsForRow[rowIdx] - 1) * seatIdxInRow)
        }
        else {
          angleDeg = (
            7 * widthConeAngle
            + widthSmallExtremConeAngle
            + widthAlleyAngle / 2
            + (widthConeAngle - widthSmallExtremConeAngle - widthAlleyAngle / 2) / (seatsForRow[rowIdx] - 1) * seatIdxInRow)
          if (rowIdx === 11) {
            angleDeg = (
              7 * widthConeAngle
              + widthSmallExtremConeAngle
              + widthAlleyAngle / 2
              + (widthConeAngle - widthSmallExtremConeAngle - widthAlleyAngle / 2) / (seatsForRow[rowIdx] - 1 + 2) * (seatIdxInRow + 1))
          }
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
  return hemicyleSeatsCoords
}


onMounted(() => {
  hemicyleSeatsCoords.data = getHemicyleCoords()
  hemicyleSeatsCoords.isLoading = false
}
)


</script>

<template>
  <Scatter v-if="!hemicyleSeatsCoords.isLoading" :data="data" :options="options" />
</template>
