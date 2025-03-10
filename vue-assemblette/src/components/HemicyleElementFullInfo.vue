<script setup lang="ts">
import { type PropType } from 'vue';
import type { HemicycleElement } from '@/entities/hemicycle-element';

defineProps({
  hemicycleElement: {
    type: Object as PropType<HemicycleElement>,
    required: true
  }
});
</script>

<template>
  <div class="flex flex-col gap-10">
    <div class="text-2xl text-gray-700">
      <p>{{ hemicycleElement.deputy.firstName }} {{ hemicycleElement.deputy.lastName }} {{ hemicycleElement.deputy.id }}
      </p>
    </div>
    <div class="flex flex-row gap-x-2 items-baseline">
      <i :class="['text-2xl', 'pi', 'pi-building-columns']"
        :style="{ color: hemicycleElement.authorityPG.associatedColor }"></i>
      <p class="text-xl text-gray-500 p-0">{{ hemicycleElement.authorityPG.label }}</p>
    </div>
    <div class="flex flex-row gap-x-2 items-baseline">
      <i class="text-2xl pi pi-map-marker text-orange-300"></i>
      <p class="text-xl text-gray-500">{{ "To fill groupe région" }}, {{ "To fill groupe département" }}</p>
    </div>
  </div>
</template>
