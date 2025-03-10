<script setup lang="ts">
import { computed } from 'vue';



const props = defineProps({
  rows: { type: Number, required: true },
  size: { type: Number, required: true },
  pagesSelectable: { type: Number, default: 0 }
});
const firstRow = defineModel<number>({ required: true })

const currentPage = computed(() => {
  return Math.floor(firstRow.value / props.rows) + 1
})

const startNumberPagesSelectable = computed(() => {
  let startValue = currentPage.value - Math.floor(props.pagesSelectable / 2)
  if (startValue <= 0) startValue = 1
  while (startValue + props.pagesSelectable - 1 > Math.floor(props.size / props.rows) + 1) startValue--
  return startValue
})

function increasePage() {
  if (firstRow.value < props.size - props.rows) {
    firstRow.value += props.rows
  }
}

function decreasePage() {
  if (firstRow.value > 0) {
    firstRow.value -= props.rows
  }
}

function goToFirstPage() {
  firstRow.value = 0
}

function goToEndPage() {
  firstRow.value = Math.floor(props.size / props.rows) * props.rows
}

function goToSpecificPage(pageNumber: number) {
  firstRow.value = (pageNumber - 1) * props.rows
}

</script>

<template>
  <div class="flex flex-row gap-2 justify-center items-baseline text-base text-gray-500">
    <button @click="goToFirstPage" class="flex rounded-full size-10 items-center justify-center"
      :class="firstRow === 0 ? 'text-gray-300' : 'text-inherit cursor-pointer hover:bg-gray-200'">
      <i class="pi pi-angle-double-left text-inherit"></i>
    </button>
    <button @click="decreasePage" class="flex rounded-full size-10 items-center justify-center"
      :class="firstRow === 0 ? 'text-gray-300' : 'text-inherit cursor-pointer hover:bg-gray-200'">
      <i class="pi pi-angle-left text-inherit"></i>
    </button>
    <div v-for="index in pagesSelectable" :key="index">
      <button @click="goToSpecificPage(startNumberPagesSelectable + index - 1)"
        class="flex rounded-full size-10 cursor-pointer items-center justify-center"
        :class="startNumberPagesSelectable + index - 1 === currentPage ? 'bg-green-700 text-gray-50' : 'hover:bg-gray-200'">
        {{ startNumberPagesSelectable + index - 1 }}
      </button>
    </div>
    <button @click="increasePage" class="flex rounded-full size-10 items-center justify-center"
      :class="firstRow === Math.floor(size / rows) * rows ? 'text-gray-300' : 'text-inherit cursor-pointer hover:bg-gray-200'">
      <i class="pi pi-angle-right text-inherit"></i>
    </button>
    <button @click="goToEndPage" class="flex rounded-full size-10 items-center justify-center"
      :class="firstRow === Math.floor(size / rows) * rows ? 'text-gray-300' : 'text-inherit cursor-pointer hover:bg-gray-200'">
      <i class="pi pi-angle-double-right text-inherit"></i>
    </button>
  </div>
</template>
