import axios from 'axios'

const restAuthorities = axios.create({
  // better still, use env vars to define your URLs
  baseURL: 'http://localhost:8080/api/authorities',
})

export default class AuthoritiesService {
  public getAuthorities = async () => {
    try {
      return (await restAuthorities.get('')).data
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        console.error(err.toJSON())
        throw new Error(err.message)
      } else {
        console.error(err)
        throw new Error('An unexpected error occurred')
      }
    }
  }
}
