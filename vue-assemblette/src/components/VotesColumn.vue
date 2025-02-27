<script setup lang="ts">
import type { Vote } from '@/entities/vote';
import { onMounted, type PropType } from 'vue';
import DeputyCard from './DeputyCard.vue';
import { useMainStore } from '@/store/store-mandates';

const store = useMainStore()

defineProps({
  name: {
    type: String,
    required: true
  },
  votes: {
    type: Object as PropType<Vote[]>,
    required: true
  }
});

interface titleStyle {
  nameToDisplay: string,
  icon: string,
  iconColor: string
}

const nameToTitleStyle = new Map<string, titleStyle>([
  ["nonVotant", {
    nameToDisplay: "Non votant",
    icon: "pi-times",
    iconColor: "text-blue-500",
  }],
  ["pour", {
    nameToDisplay: "Pour",
    icon: "pi-thumbs-up-fill",
    iconColor: "text-green-600",
  }],
  ["contre", {
    nameToDisplay: "Contre",
    icon: "pi-thumbs-down-fill",
    iconColor: "text-red-600",
  }],
  ["abstention", {
    nameToDisplay: "Abstention",
    icon: "pi-stop",
    iconColor: "text-amber-400",
  }],
  ["nonVotantVolontaire", {
    nameToDisplay: "Non votant volontaire",
    icon: "pi-times-circle",
    iconColor: "text-blue-700",
  }],
])

onMounted(
  store.getMandatesByDeputies
)

</script>

<template>
  <div v-if="!store.mandatesByDeputies.isLoading"
    class="flex flex-col flex-none justify-start w-xs gap-y-5 p-5 gap-x-2 rounded-xl bg-white shadow-lg outline outline-black/5">
    <div class="flex flex-row gap-x-2 items-baseline">
      <i :class="['text-base', 'pi', nameToTitleStyle.get(name)!.icon, nameToTitleStyle.get(name)!.iconColor]"></i>
      <p class="text-base">{{ nameToTitleStyle.get(name)!.nameToDisplay }}</p>
    </div>
    <DeputyCard v-for="vote in votes" :key="vote.id" :deputy="vote.deputy"
      :mandates="store.mandatesByDeputies.data[vote.deputy.id]" />
  </div>
</template>
