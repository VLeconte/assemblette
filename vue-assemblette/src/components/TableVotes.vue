<script setup lang="ts">
import { type PropType } from 'vue';
import type { PoliticalGroupVotes } from '@/entities/political-group-votes'

defineProps({
  politicalGroupsVotes: {
    type: Object as PropType<PoliticalGroupVotes[]>,
    required: true
  }
});

interface ColumnTitle {
  nameToDisplay: string,
  icon: string,
  iconColor: string
}

const columnTitles: ColumnTitle[] = [
  {
    nameToDisplay: "Groupe politique",
    icon: "pi-times",
    iconColor: "text-blue-500",
  },
  {
    nameToDisplay: "Pour",
    icon: "pi-thumbs-up-fill",
    iconColor: "text-green-600",
  },
  {
    nameToDisplay: "Contre",
    icon: "pi-thumbs-down-fill",
    iconColor: "text-red-600",
  },
  {
    nameToDisplay: "Abstention",
    icon: "pi-stop",
    iconColor: "text-amber-400",
  },
  {
    nameToDisplay: "Non votant",
    icon: "pi-times",
    iconColor: "text-blue-500",
  }
]

function getColumnTitleClass(columTitle: ColumnTitle) {
  return {
    'justify-center': columTitle.nameToDisplay !== 'Groupe politique'
  };
}

function getBackground(index: number) {
  return index % 2 === 0 ? 'bg-gray-200' : 'bg-white'
}

</script>

<template>
  <div class="grid grid-cols-5 p-4 rounded-xl bg-white">
    <div v-for="columTitle in columnTitles" :key="columTitle.nameToDisplay"
      class="flex flex-row items-baseline gap-3 p-2" :class="getColumnTitleClass(columTitle)">
      <i :class="['text-base', 'pi', columTitle.icon, columTitle.iconColor]"></i>
      <p class="text-base font-medium hidden md:block">{{ columTitle.nameToDisplay.toUpperCase() }}</p>
    </div>
    <template v-for="(politicalGroup, index) in politicalGroupsVotes" :key="politicalGroup.politicalGroupLabel">
      <div class="text-base p-2 truncate" :class="getBackground(index)"> {{ politicalGroup.politicalGroupLabel
        }}</div>
      <div class="text-base text-center p-2" :class="getBackground(index)">{{ politicalGroup.for }}</div>
      <div class="text-base text-center p-2" :class="getBackground(index)">{{ politicalGroup.against }}</div>
      <div class="text-base text-center p-2" :class="getBackground(index)">{{ politicalGroup.nonVoting }}</div>
      <div class="text-base text-center p-2" :class="getBackground(index)">{{ politicalGroup.abstention }}</div>
    </template>
  </div>
</template>
