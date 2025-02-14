<script setup lang="ts">
import { type Deputy } from '@/entities/deputy';
import type { PropType } from 'vue';
defineProps({
  deputy: {
    type: Object as PropType<Deputy>,
    required: true
  }
});

const getPoliticalColor = function (politicalGroup: string) {
  switch (politicalGroup) {
    case 'Rassemblement National':
      return "text-blue-900"
    case 'Ensemble pour la République':
      return "text-yellow-300"
    case 'La France insoumise - Nouveau Front Populaire':
      return "text-red-700"
    case 'Socialistes et apparentés':
      return "text-red-300"
    case 'Droite Républicaine':
      return "text-sky-500"
    case 'Écologiste et Social':
      return "text-lime-500"
    case 'Les Démocrates':
      return "text-amber-500"
    case 'Horizons & Indépendants':
      return "text-blue-700"
    case 'Libertés, Indépendants, Outre-mer et Territoires':
      return "text-pink-300"
    case 'Gauche Démocrate et Républicaine':
      return "text-red-500"
    case 'UDR':
      return "text-blue-950"
    case 'Non-inscrit':
      return "text-stone-400"
    default:
      return "text-gray-500"
  }
}
</script>

<template>
  <div class="flex-none grid grid-cols-1 gap-3 w-2xs p-3 rounded-xl bg-white shadow-md outline outline-black/5">
    <div class="text-base text-gray-700">
      <p>{{ deputy.firstName }} {{ deputy.lastName }}</p>
    </div>
    <div class="flex flex-row gap-x-2 items-baseline">
      <i :class="['text-sm', 'pi', 'pi-building-columns', getPoliticalColor(deputy.politicalGroupFull)]"></i>
      <p class="text-sm text-gray-500 p-0">{{ deputy.politicalGroupFull }}</p>
    </div>
    <div class="flex flex-row gap-x-2 items-baseline">
      <i class="text-sm pi pi-map-marker text-orange-300"></i>
      <p class="text-sm text-gray-500">{{ deputy.region }}, {{ deputy.department }}</p>
    </div>
  </div>
</template>
